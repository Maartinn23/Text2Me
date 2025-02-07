package sintesis.text2me.models;

import java.util.List;

import jakarta.persistence.*;



@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ChatType chatType = ChatType.PRIVATE;

    @ManyToMany
    @JoinTable(
        name = "chat_users",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> chatParticipants;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> chatMessages;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChatType getChatType() {
		return chatType;
	}

	public void setChatType(ChatType chatType) {
		this.chatType = chatType;
	}

	public List<AppUser> getChatParticipants() {
		return chatParticipants;
	}

	public void setChatParticipants(List<AppUser> chatParticipants) {
		this.chatParticipants = chatParticipants;
	}

	public List<Message> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<Message> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public Chat(int id, String name, ChatType chatType, List<AppUser> chatParticipants, List<Message> chatMessages) {
		super();
		this.id = id;
		this.name = name;
		this.chatType = chatType;
		this.chatParticipants = chatParticipants;
		this.chatMessages = chatMessages;
	}
    
    public Chat(){
    	
    }
    
    
    
    
    
    
    
}

