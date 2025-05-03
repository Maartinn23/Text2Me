package sintesis.text2me.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.models.Chat;

public interface ChatRepository extends JpaRepository<Chat,Integer>{
	
	Optional<Chat> findByChatParticipantsContainsAndChatParticipantsContains(AppUser userX, AppUser userY);
	
	List<Chat> findByChatParticipantsContaining(AppUser user);
	
}
