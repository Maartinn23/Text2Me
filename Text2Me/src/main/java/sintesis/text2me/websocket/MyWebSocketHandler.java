package sintesis.text2me.websocket;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.models.Chat;
import sintesis.text2me.models.ChatType;
import sintesis.text2me.models.Message;
import sintesis.text2me.repositories.AppUserRepository;
import sintesis.text2me.repositories.ChatRepository;
import sintesis.text2me.repositories.MessageRepository;
import sintesis.text2me.services.AppUserService;
import sintesis.text2me.services.AuthenticatorService;

// TODO: Acabar con la persistencia de mensajes y chats...


@Component
public class MyWebSocketHandler extends TextWebSocketHandler{

	private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	
	@Autowired
	AppUserService appUserService;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	ChatRepository chatRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	private final AuthenticatorService autheticatorService;
	
	
	public MyWebSocketHandler(AuthenticatorService autheticatorService) {
		this.autheticatorService = autheticatorService;
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session);
		System.out.println("Nueva connexion al sistema! " + session.getId());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		
		
		
		Object principal = session.getPrincipal(); // Obtenim dades de la sessió actual (usuari actual)
		
		String senderEmail = "desconegut@example.com";
		String senderName = "desconegut";
		
		
		
		// Aqui definim si l'objecte de la sessió actual té relació amb la informació de l'usuari, y establim les seves dades.
		 
		if(!(principal instanceof Authentication auth) || 
			!(auth.getPrincipal() instanceof UserDetails userDetails)) {
				return; // si no hi ha relació, no retornem res.
					
		}
		
		senderEmail = userDetails.getUsername(); // Obtenim el correu del remitent en base a la sessió actual (usuari loguejat)
		senderName = appUserService.getUserLogged(senderEmail); // Obtenim el nom del remitent en base al correu obtingut adalt.
		final AppUser sender = appUserRepository.findByEmail(senderEmail).orElseThrow(); // Definim l'objecte de l'usuari remitent per posteriorment persitir dades sota el seu nom.
																						// Si no troba res, informem de l'error (try-catch simplificat)
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode  json = mapper.readTree(message.getPayload());
		
		String content = json.get("message").asText(); // del Json amb el missatge y el receptor, agafem el missatge.
		String receiverEmail = json.get("to").asText(); // del Json amb el missatge y el receptor, agafem el receptor.
		
		// Comprovem que l'email del receptor es válid i si no informem de l'error (try-catch simplificat).
		
		Optional<AppUser> optionalReceiver = appUserRepository.findByEmail(receiverEmail);
		
		System.out.println("Receptor: " + receiverEmail);
		
		
		
		/*
		
		if(optionalReceiver.isEmpty()) {
			System.out.println("Error, el receptor es null o incorrecte!!");
			return;
		} */
		
		final AppUser receiver = optionalReceiver.get(); 
		
		
		
		
		Optional<Chat> optionalChat = chatRepository.findByChatParticipantsContainsAndChatParticipantsContains(sender, receiver); // Creem el chat entre el remitent i el receptor.
		
		Chat chat = optionalChat.orElseGet(() -> { // Creem el chat entre el remitent i el receptor.
			Chat newChat = new Chat(); 
			newChat.setChatParticipants(List.of(sender, receiver));
			newChat.setChatType(ChatType.PRIVATE);
			return chatRepository.save(newChat);
		});
		
		
		Message newMessage = new Message();
		newMessage.setChat(chat);
		newMessage.setSender(sender);   
		newMessage.setContent(content);
		messageRepository.save(newMessage);
		
	    String responseJson = String.format("{\"from\": \"%s\", \"to\": \"%s\", \"message\": \"%s\"}", senderEmail, receiverEmail, content); // Json de resposta.
		
	    /*Aqui definim si l'objecte gestor dels missatges té relació amb la informació de l'usuari, 
	     * y enviem els missatges entre emisor y receptor de manera exclusiva entre tots dos.
	    */

		for(WebSocketSession ws: sessions) {
			if(ws.getPrincipal() instanceof Authentication authWebsocket) {
				if(authWebsocket.getPrincipal() instanceof UserDetails userDetailsWebSocket) {
					String userEmail = userDetailsWebSocket.getUsername(); 
					if(userEmail.equals(senderEmail) || userEmail.equals(receiverEmail)) {
						ws.sendMessage(new TextMessage(responseJson));	// enviem el missatge (Json de resposta)
					}
					
				}
			}
		}
		
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session); // tanquem la connexió
	}
	
	
	
}
