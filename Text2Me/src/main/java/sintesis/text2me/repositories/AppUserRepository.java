package sintesis.text2me.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sintesis.text2me.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	Optional<AppUser> findByEmail(String email); // Filtra els usuaris en base a un email proporcionat.
	
	List<AppUser> findByFirstNameContainingIgnoreCase(String name);	/* Funciona en base a un buscador d'usuaris per nom, que, obté 
																	nom i correus asociats a les paraules claus introduides 
																	al buscador. */
	
	
	
	
}
