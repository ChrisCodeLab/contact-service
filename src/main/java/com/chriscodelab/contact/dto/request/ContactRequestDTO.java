package com.chriscodelab.contact.dto.request;

import jakarta.validation.constraints.*;

public class ContactRequestDTO {
	
	@NotBlank(message="Name is required")
	@Size(max=100)
	private String name;
	
	@NotBlank(message="Email is required")
	@Email(message="Invalid Email Format")
	@Size(max=150)
	private String email;
	
	@NotBlank(message="Message is required")
	@Size(max=1000)
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "ContactRequestDTO [name=" + name + ", email=" + email + ", message=" + message + "]";
	}

	public ContactRequestDTO() {
		super();
	}

	public ContactRequestDTO(@NotBlank(message = "Name is required") @Size(max = 100) String name,
			@NotBlank(message = "Email is required") @Email(message = "Invalid Email Format") @Size(max = 150) String email,
			@NotBlank(message = "Message is required") @Size(max = 1000) String message) {
		super();
		this.name = name;
		this.email = email;
		this.message = message;
	}
}



