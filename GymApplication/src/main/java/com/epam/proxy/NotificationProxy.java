package com.epam.proxy;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.dto.response.NotificationDto;
@FeignClient(name = "notification-app")
@LoadBalancerClient(name="notification-app")
public interface NotificationProxy {
	
	@PostMapping("/notification")
	public void addNewNotificationLog(@RequestBody NotificationDto dto);
}
