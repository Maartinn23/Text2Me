package sintesis.text2me.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sintesis.text2me.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

	Optional<AppUser> findByEmail(String email);
	
}
