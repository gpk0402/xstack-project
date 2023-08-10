package com.epam.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.epam.dto.response.NotificationDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationProducer {
	@Autowired
	private final KafkaTemplate<String, NotificationDto> kafkaTemplate;
	
	public void sendNotification(NotificationDto notificationDto) {
		kafkaTemplate.send("notification-topic", notificationDto);
	}
	

}
