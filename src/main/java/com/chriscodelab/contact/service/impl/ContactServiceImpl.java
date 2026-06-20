package com.chriscodelab.contact.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chriscodelab.contact.domain.entity.ContactMessageEntity;
import com.chriscodelab.contact.domain.repository.ContactMessageRepository;
import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.dto.response.ContactResponseDTO;
import com.chriscodelab.contact.mapper.ContactMapper;
import com.chriscodelab.contact.service.ContactService;
import com.chriscodelab.contact.service.EmailService;

import jakarta.transaction.Transactional;

@Service
public class ContactServiceImpl implements ContactService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);
	
	private final ContactMessageRepository repository;
	private final ContactMapper mapper;
	private final EmailService emailService;
	
	//constructor injection of dependencies(auto injection)
	public ContactServiceImpl(ContactMessageRepository repository, ContactMapper mapper, EmailService emailService) {
		this.repository = repository;
		this.mapper = mapper;
		this.emailService = emailService;
	}
	
	@Override
	@Transactional
	public ContactResponseDTO submitContact(ContactRequestDTO request) {
		ContactMessageEntity entity = mapper.toEntity(request);
		ContactMessageEntity savedEntity = repository.save(entity);
		
		try {
			emailService.sendContactEmails(request);
		} catch (Exception ex) {
			LOGGER.error("Failed to send contact notification emails", ex);
		}
		
		ContactResponseDTO response = mapper.toResponseDTO(savedEntity);
		return response;
	}
	
}
