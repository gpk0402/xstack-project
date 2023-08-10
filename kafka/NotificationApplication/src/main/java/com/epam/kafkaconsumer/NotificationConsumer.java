package com.epam.kafkaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.dto.response.NotificationDto;
import com.epam.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationConsumer {

	@Autowired
	NotificationService notificationService;
	
	@KafkaListener(topics = "notification-topic", groupId = "notification")
	public void sendNotification(NotificationDto notificationDto) {
		log.info("Consumed notification dto: ", notificationDto);
		notificationService.addNewNotificationLog(notificationDto);
		
	}
}
