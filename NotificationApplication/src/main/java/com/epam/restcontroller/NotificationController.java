package com.epam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.NotificationDto;
import com.epam.service.NotificationServiceImpl;

@RestController
@RequestMapping("notification")
public class NotificationController {
	
	@Autowired
	NotificationServiceImpl notificationServiceImpl;
	

	@PostMapping
	public void addNewNotificationLog(@RequestBody NotificationDto dto)
	{
		notificationServiceImpl.addNewNotificationLog(dto);
	}
}
