package sintesis.text2me.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import sintesis.text2me.controllers.ChatController;
import sintesis.text2me.repositories.AppUserRepository;

@Service
public class AuthenticatorService {

	@Autowired
	AppUserRepository repo;
	


	public String getLoggedUserName() {
        
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !authentication.isAuthenticated()) {
			return "Guest";
		}
		
		if(authentication.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) authentication.getPrincipal()).getUsername();
		}
		
		return "ERROR";
		

    }
}
