package com.epam.service;

import com.epam.dto.request.UpdatePassword;
import com.epam.dto.response.CredentialsDto;

public interface UserService {
	boolean validateUser(CredentialsDto credentials);
	void changeLogin(UpdatePassword updatePassword);
	

}
