package sintesis.text2me.models;

import java.time.LocalDateTime;

import javax.crypto.SecretKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message { // Classe que defineix com estructurem els missatges a l'aplicació.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "remitent_id", nullable = false)
    private AppUser sender;

    @Column(nullable = false)
    private String content;

    private boolean readingState = false;

    @Column(nullable = false)
    private LocalDateTime sendingDate = LocalDateTime.now();
    
    @Column(length = 512)
    private String iv;
    
    @Column(length = 512)
    private String sK;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public AppUser getSender() {
		return sender;
	}

	public void setSender(AppUser sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isReadingState() {
		return readingState;
	}

	public void setReadingState(boolean readingState) {
		this.readingState = readingState;
	}

	public LocalDateTime getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(LocalDateTime sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getSk() {
		return sK;
	}

	public void setSk(String sK) {
		this.sK = sK;
	}

    public Message(int id, Chat chat, AppUser sender, String content, boolean readingState, LocalDateTime sendingDate,
			String iv, String sK) {
		super();
		this.id = id;
		this.chat = chat;
		this.sender = sender;
		this.content = content;
		this.readingState = readingState;
		this.sendingDate = sendingDate;
		this.iv = iv;
		this.sK = sK;
	}

	public Message(){
    	
    }
    
    
    
    
    
    
}

