package sintesis.text2me.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.repositories.AppUserRepository;

@Service
public class AuthenticatorService {

	@Autowired
	AppUserRepository repo;
	
	/*
	 * Aquest metode ens retorna l'usuari loguejat actual.
	 */
	
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
