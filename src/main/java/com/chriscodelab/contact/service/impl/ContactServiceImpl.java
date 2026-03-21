package com.chriscodelab.contact.service.impl;

import org.springframework.stereotype.Service;

import com.chriscodelab.contact.domain.entity.ContactMessageEntity;
import com.chriscodelab.contact.domain.repository.ContactMessageRepository;
import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.dto.response.ContactResponseDTO;
import com.chriscodelab.contact.mapper.ContactMapper;
import com.chriscodelab.contact.service.ContactService;

import jakarta.transaction.Transactional;

@Service
public class ContactServiceImpl implements ContactService{
	
	private final ContactMessageRepository repository;
	private final ContactMapper mapper;
	
	//constructor injection of dependencies(auto injection)
	public ContactServiceImpl(ContactMessageRepository repository, ContactMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public ContactResponseDTO submitContact(ContactRequestDTO request) {
		
		ContactMessageEntity entity = mapper.toEntity(request);
		ContactMessageEntity savedEntity = repository.save(entity);
		ContactResponseDTO response = mapper.toResponseDTO(savedEntity);
		
		return response;
		
	}
	
}
