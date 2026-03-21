package com.chriscodelab.contact.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.dto.response.ApiResponse;
import com.chriscodelab.contact.dto.response.ContactResponseDTO;
import com.chriscodelab.contact.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
	
	private final ContactService contactService;
	
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}
	
	@PostMapping("/submit")
	public ResponseEntity<ApiResponse<ContactResponseDTO>> submitContact(@Valid @RequestBody ContactRequestDTO request){
		ContactResponseDTO response = contactService.submitContact(request);
		ApiResponse<ContactResponseDTO> apiResponse = new ApiResponse<>(true, "Contact Submission Successful", response);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

}
