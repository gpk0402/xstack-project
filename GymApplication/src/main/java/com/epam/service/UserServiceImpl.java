package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.request.UpdatePassword;
import com.epam.dto.response.CredentialsDto;
import com.epam.entity.User;
import com.epam.exception.UserException;
import com.epam.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	static final String USER_EXCEPTION = "User with username & password not found"; 
	
	@Override
	public boolean validateUser(CredentialsDto credentials) {
		log.info("Entered into validateUser");
		return userRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword()).map(user->{
			log.info("Login successfull");
			return true;
		}).orElseThrow(() -> new UserException(USER_EXCEPTION));
		 
	}
	
	@Override
	@Transactional
	public void changeLogin(UpdatePassword updatePassword) {
		log.info("Entered into update Password method");
		User user=userRepository.findByUsernameAndPassword(updatePassword.getUserName(), updatePassword.getOldPassword()).orElseThrow(()->new UserException(USER_EXCEPTION));
		user.setPassword(updatePassword.getNewPassword());
	}

}
