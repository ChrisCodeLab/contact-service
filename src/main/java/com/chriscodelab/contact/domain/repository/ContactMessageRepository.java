package com.chriscodelab.contact.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chriscodelab.contact.domain.entity.ContactMessageEntity;

public interface ContactMessageRepository extends JpaRepository<ContactMessageEntity, Long>{
	
	

}
