package com.chriscodelab.contact.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.chriscodelab.contact.domain.entity.ContactMessageEntity;
import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.dto.response.ContactResponseDTO;
import com.chriscodelab.contact.mapper.config.GlobalMapperConfig;



@Mapper(config=GlobalMapperConfig.class)
public interface ContactMapper {
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="createdAt", ignore=true)
	ContactMessageEntity toEntity(ContactRequestDTO request);
	
	@Mapping(target="timestamp", source="createdAt")
	@Mapping(target="status", expression="java(\"Message received successfully\")")
	ContactResponseDTO toResponseDTO(ContactMessageEntity entity);

}
