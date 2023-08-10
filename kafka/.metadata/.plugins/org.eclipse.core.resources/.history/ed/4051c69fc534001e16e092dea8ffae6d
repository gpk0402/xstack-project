package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.epam.dto.NotificationDto;
import com.epam.model.Notification;
import com.epam.repository.EmailRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	EmailRepository emailRepository;
	
	@Value("${from.mail}")
	private String fromMail;

	public void addNewNotificationLog(NotificationDto dto) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromMail);
		mailMessage.setTo(dto.getToEmails().toArray(new String[0]));
		mailMessage.setCc(dto.getCcEmails().toArray(new String[0]));
		mailMessage.setSubject(dto.getSubject());
		mailMessage.setText(dto.getBody());

		Notification notification = Notification.builder().fromEmail(fromMail).toEmails(dto.getToEmails())
				.ccEmails(dto.getCcEmails()).body(dto.getBody()).build();
		System.out.println(dto);
		try {
			javaMailSender.send(mailMessage);
			notification.setStatus("Mail Sent");
			notification.setRemarks("Mail sent successfully!!!");
		} catch (MailException e) {
			notification.setStatus("Mail failed");
			notification.setRemarks("Failed to send a mail " + e.getMessage());
		}
		emailRepository.save(notification);

	}

}
