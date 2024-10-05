package com.project.schoolmanagment.payload.mappers;


import com.project.schoolmanagment.entity.concretes.business.ContactMessage;
import com.project.schoolmanagment.payload.request.businnes.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.businnes.ContactMessageResponse;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ContactMessageMapper {

	public ContactMessageResponse contactMessageToResponse(ContactMessage contactMessage){
		return ContactMessageResponse.builder()
				.name(contactMessage.getName())
				.subject(contactMessage.getSubject())
				.message(contactMessage.getMessage())
				.email(contactMessage.getEmail())
				.dateTime(LocalDateTime.now())
				.build();
	}

	//TODO please check builder design pattern
	//I would give this method a name like mapContactMessageRequestToContactMessage
	public ContactMessage requestToContactMessage(ContactMessageRequest contactMessageRequest){
		return ContactMessage.builder()
				.name(contactMessageRequest.getName())
				.subject(contactMessageRequest.getSubject())
				.message(contactMessageRequest.getMessage())
				.email(contactMessageRequest.getEmail())
				.dateTime(LocalDateTime.now())
				.build();
	}
}
