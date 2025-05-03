package sintesis.text2me.models;

import java.time.LocalDateTime;


/*
 * Classe per generar els missatges enmagatzemats a bbdd.
 */


public class MessageDto {

	private String from;
	private String content;
	private LocalDateTime timestamp;
	private String imageUrl;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public MessageDto(String from, String content, LocalDateTime timestamp, String imageUrl) {
		super();
		this.from = from;
		this.content = content;
		this.timestamp = timestamp;
		this.imageUrl = imageUrl;
	}
	
	
	
	
	
	
	
}
