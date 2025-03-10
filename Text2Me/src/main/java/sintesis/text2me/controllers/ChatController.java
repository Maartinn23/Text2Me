/*package sintesis.text2me.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.repositories.AppUserRepository;

@RestController
public class ChatController {
	
	
	@Autowired 
	AppUserRepository repo;
	
	//@Bean
	//UserDetailsService
	
	@GetMapping("/xats")
	public String loadUserById(@PathVariable int id) throws UsernameNotFoundException {
		Optional<AppUser> user = repo.findById(id);
		
		System.out.println(user);
		
		if (user.isPresent()) {
			var userInfo = user.get();
			System.out.println(userInfo.getFirstName());
			return userInfo.getFirstName();
		}
		else {
			throw new UsernameNotFoundException("Error! aquest usuari no existeix al sistema...");
		}
		
	
		
	}
	
	
}
*/