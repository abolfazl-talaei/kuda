package com.kuda.app.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;

@Component
public class SecurityUtil {

	public static String hash(String data) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(data);
	}

	public static boolean validate(String passwordHash, String password) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, passwordHash);
	}

	public String getDefaultUsername(String username) {

		return getOrganizationUsername(username);
	}

	public String getReaderUsername(String username) throws KudaException {

		if (username == null) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.INTERNAL_SERVER_ERROR,
					Resources.UTIL.getName(), "101");
		}
		return KudaRole.getUsernamePattern(KudaRole.READER, getOrganizationUsername(username));
	}

	public String getAdminUsername(String username) throws KudaException {

		if (username == null) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.INTERNAL_SERVER_ERROR,
					Resources.UTIL.getName(), "102");
		}
		return KudaRole.getUsernamePattern(KudaRole.ADMIN, getOrganizationUsername(username));
	}

	public static String getOrganizationUsername(String username) {

		if (username.endsWith(KudaRole.getPattern(KudaRole.ADMIN))) {
			return username.replace(KudaRole.getPattern(KudaRole.ADMIN), "");
		}
		if (username.endsWith(KudaRole.getPattern(KudaRole.READER))) {
			return username.replace(KudaRole.getPattern(KudaRole.READER), "");
		}
		return username;
	}

	public static String getUserRole(String username) {

		if (username.endsWith(".".concat(KudaRole.READER))) {
			return KudaRole.READER;
		} else if (username.endsWith(".".concat(KudaRole.ADMIN))) {
			return KudaRole.ADMIN;
		} else {
			return KudaRole.DEFAULT;
		}
	}
}
