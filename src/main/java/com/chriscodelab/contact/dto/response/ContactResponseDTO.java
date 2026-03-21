package com.chriscodelab.contact.dto.response;

import java.time.LocalDateTime;


public class ContactResponseDTO {

	private String status;
	private LocalDateTime timestamp;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "ContactResponseDTO [status=" + status + ", timestamp=" + timestamp + "]";
	}
	
	public ContactResponseDTO() {
		super();
	}
	
	public ContactResponseDTO(String status, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.timestamp = timestamp;
	}	
}
