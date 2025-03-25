package sintesis.text2me.websocket;

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

import sintesis.text2me.services.AppUserService;
import sintesis.text2me.services.AuthenticatorService;
@Component
public class MyWebSocketHandler extends TextWebSocketHandler{

	private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	
	@Autowired
	AppUserService appUserService;
	
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
		
		System.out.println("Nou Missatge: " + message.getPayload());
		
		Object principal = session.getPrincipal();
		String username = "Desconegut";
		
		if(principal instanceof Authentication) {
			Authentication authentication = (Authentication) principal;
			if(authentication.getPrincipal() instanceof UserDetails) {
				username = ((UserDetails) authentication.getPrincipal()).getUsername();
				username = appUserService.getUserLogged(username);

			}
		}
		
		
		for(WebSocketSession webSocketSession: sessions ) {
			webSocketSession.sendMessage(new TextMessage( "("+username+") " + message.getPayload()));
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session);
		System.out.println("Connexió tancada, Adeu!!! " + session.getId());
	}
	
	
	
}
