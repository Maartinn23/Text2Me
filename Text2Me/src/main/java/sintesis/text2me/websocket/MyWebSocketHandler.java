package sintesis.text2me.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

	private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session);
		System.out.println("Nueva connexion al sistema! " + session.getId());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		
		System.out.println("Nuevo mensaje recibido: " + message.getPayload());
		
		for(WebSocketSession webSocketSession: sessions ) {
			webSocketSession.sendMessage(new TextMessage("Servidor " + message.getPayload()));
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session);
		System.out.println("Connexión cerrada, hasta la proxima! " + session.getId());
	}
	
	
	
}
