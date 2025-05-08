package sintesis.text2me.services;

import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sintesis.text2me.models.Message;
import sintesis.text2me.repositories.MessageRepository;

// servei que agrupa la logica d'encriptament de missatges enviats desde el websocket.
@Service
public class MessageService {
	
	
	@Autowired
	CryptoService cryptoService;
	
	@Autowired
	MessageRepository messageRepository;
	
	public void saveMessageEncrypted(Message message) throws Exception {
		
		SecretKey sK = cryptoService.generateAesKey();
		IvParameterSpec iv = cryptoService.generateIv();
		
		String messageEncrypted = cryptoService.encryptMessage(message.getContent(), sK, iv);
		
		Message newMessage = new Message();
		newMessage.setChat(message.getChat());
		newMessage.setSender(message.getSender());
		newMessage.setContent(messageEncrypted);
		newMessage.setIv(Base64.getEncoder().encodeToString(iv.getIV()));
	    newMessage.setSk(Base64.getEncoder().encodeToString(sK.getEncoded()));
		
		messageRepository.save(newMessage);
		
	}	
	
}
