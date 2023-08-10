package com.epam.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.request.UpdatePassword;
import com.epam.dto.response.CredentialsDto;
import com.epam.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@PostMapping("/login")
	@ResponseStatus(code = HttpStatus.OK)
	public void userLogin(@Valid @RequestBody CredentialsDto credentials)
	{
		log.info("Entered into validate Login {}  Method RestController :{}",credentials);
		userServiceImpl.validateUser(credentials);
	}
	
	@PutMapping("/updatePassword")
	@ResponseStatus(code = HttpStatus.OK)
	public void changeLogin(@Valid @RequestBody UpdatePassword updatePassword)
	{
		log.info("Entered into chnage Login {} Method RestController :{}",updatePassword);
		userServiceImpl.changeLogin(updatePassword);
	}

}
