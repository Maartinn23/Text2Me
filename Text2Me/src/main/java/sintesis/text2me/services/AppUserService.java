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
	private AppUserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<AppUser> user = repo.findByEmail(email);

		System.out.println(user);

		if (user.isPresent()) {

			var userInfo = user.get();
			return User.builder().username(userInfo.getEmail()).password(userInfo.getPassword()).build();

		} else {
			System.out.println("Error! aquest usuari no existeix al sistema... ");
			throw new UsernameNotFoundException(email);
		}

	}

}