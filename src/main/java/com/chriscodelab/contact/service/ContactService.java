package com.chriscodelab.contact.service;

import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.dto.response.ContactResponseDTO;

public interface ContactService {
	
	ContactResponseDTO submitContact(ContactRequestDTO request);

}
