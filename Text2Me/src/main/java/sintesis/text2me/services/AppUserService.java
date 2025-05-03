package sintesis.text2me.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.repositories.AppUserRepository;

@Service
public class AppUserService implements UserDetailsService {

	@Autowired
	private AppUserRepository appUserRepository;
	
	// Métode implementat que carrega l'usuari de la bbdd en base a un email proporcionat.

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<AppUser> user = appUserRepository.findByEmail(email);

		if (user.isPresent()) {

			var userInfo = user.get();
			return User.builder().username(userInfo.getEmail()).password(userInfo.getPassword()).build();

		} else {
			System.out.println("Error! aquest usuari no existeix al sistema... ");
			throw new UsernameNotFoundException(email);
		}

	}
	
	// Amb aquest métode obtenim dades més concretes de l'usuari en base al seu email.
	
	public String getUserLogged(String email) throws UsernameNotFoundException  { 
		
		
		Optional<AppUser> user = appUserRepository.findByEmail(email);
		
		if (user.isPresent()) {
			
			var userInfo = user.get();
			
			return userInfo.getFirstName();
		}
		else {
			System.out.println("Error! aquest usuari no existeix al sistema... ");
			throw new UsernameNotFoundException(email);
		}
		
	}
	
	

}