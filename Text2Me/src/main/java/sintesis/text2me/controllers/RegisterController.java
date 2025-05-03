package sintesis.text2me.controllers;

import java.security.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sintesis.text2me.config.SecurityConfig;
import sintesis.text2me.models.AppUser;
import sintesis.text2me.repositories.AppUserRepository;

@RestController
public class RegisterController {

	@Autowired
	private AppUserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@PostMapping(value = "/register", consumes = "application/json")
	public AppUser createUser(@RequestBody AppUser newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Xifratge del password
		
		if (newUser.getRole() == null || newUser.getRole().isEmpty()) {
			newUser.setRole("user"); // Rol per defecte
		}
	
		return repo.save(newUser);
		
		
	}
	
	
	
}
