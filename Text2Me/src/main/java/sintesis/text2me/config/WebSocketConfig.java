package sintesis.text2me.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import sintesis.text2me.websocket.MyWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	
	private final MyWebSocketHandler myWebSocketHandler;
	
	public WebSocketConfig( MyWebSocketHandler myWebSocketHandler) {
		this.myWebSocketHandler = myWebSocketHandler;
	}
	
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(myWebSocketHandler, "/ws")
         .setAllowedOrigins("*")
         .setHandshakeHandler(new DefaultHandshakeHandler(){
			 
			 @Override
			 protected Principal determineUser(org.springframework.http.server.ServerHttpRequest request, 
                     org.springframework.web.socket.WebSocketHandler wsHandler, 
                     Map<String, Object> attributes) {
				 
				 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				 return (authentication != null && authentication.isAuthenticated() ? authentication : null);
			 }
		 });
	}

	
	
	
}
