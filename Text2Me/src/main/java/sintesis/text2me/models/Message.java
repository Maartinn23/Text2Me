package sintesis.text2me.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.Table;

@Entity
@Table(name = "missatges")
public class Message {

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

	public Message(int id, Chat chat, AppUser sender, String content, boolean readingState, LocalDateTime sendingDate) {
		super();
		this.id = id;
		this.chat = chat;
		this.sender = sender;
		this.content = content;
		this.readingState = readingState;
		this.sendingDate = sendingDate;
	}

    public Message(){
    	
    }
    
    
    
    
    
    
}

