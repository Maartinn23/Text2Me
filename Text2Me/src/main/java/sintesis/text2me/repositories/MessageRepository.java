package sintesis.text2me.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sintesis.text2me.models.Chat;
import sintesis.text2me.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	public List<Message> findByChatOrderBySendingDateAsc(Chat chat);
	
}
