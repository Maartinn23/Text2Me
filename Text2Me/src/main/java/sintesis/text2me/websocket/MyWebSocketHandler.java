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
import sintesis.text2me.services.CryptoService;
import sintesis.text2me.services.MessageService;



@Component
public class MyWebSocketHandler extends TextWebSocketHandler{

	private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	
	@Autowired
	AppUserService appUserService;
	
	@Autowired
	CryptoService cryptoService;
	
	@Autowired
	MessageService messageService;
	
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
	
	/*
	 * Amb Aquest métode definim el chat detectant la sessió actual (usuari loguejat que envia o rep missatges) 
	 * i en base a si envia missatges, fa les peticions get o post (llegir missatges o enviar missatges) actuant sobre la 
	 * bbdd i el front-end mitjançant objectes tipus JSON. També gestiona la creació dels chats entre els usuaris i
	 * els persisteix.
	 */
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			
		Object principal = session.getPrincipal(); 
		
		String senderEmail = "desconegut@example.com";
		String senderName = "desconegut";
		
		 
		if(!(principal instanceof Authentication auth) || 
			!(auth.getPrincipal() instanceof UserDetails userDetails)) {
				return; 
					
		}
		
		senderEmail = userDetails.getUsername(); 
		senderName = appUserService.getUserLogged(senderEmail); 
		final AppUser sender = appUserRepository.findByEmail(senderEmail).orElseThrow(); 
																						
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode  json = mapper.readTree(message.getPayload());
		
		String content = json.get("message").asText();
		String receiverEmail = json.get("to").asText(); 
		
		
		Optional<AppUser> optionalReceiver = appUserRepository.findByEmail(receiverEmail);		
		
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
		messageService.saveMessageEncrypted(newMessage);
		
	    String responseJson = String.format("{\"from\": \"%s\", \"to\": \"%s\", \"message\": \"%s\"}", senderEmail, receiverEmail, content); // Json de resposta.

		for(WebSocketSession ws: sessions) {
			if(ws.getPrincipal() instanceof Authentication authWebsocket) {
				if(authWebsocket.getPrincipal() instanceof UserDetails userDetailsWebSocket) {
					String userEmail = userDetailsWebSocket.getUsername(); 
					if(userEmail.equals(senderEmail) || userEmail.equals(receiverEmail)) {
						ws.sendMessage(new TextMessage(responseJson));	
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
