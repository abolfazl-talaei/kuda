package com.kuda.app.service.organization.authentication;

import com.kuda.app.dto.response.LoginInfo;
import com.kuda.app.exception.KudaException;

public interface OrganizationAuthenticationService {

	LoginInfo signIn(String username, String password) throws KudaException;

	void signOut(String token);

	LoginInfo validateTokenStatus(String token) throws KudaException;
}
