package com.kuda.app.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.response.DefaultSuccessResponse;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.KudaException;
import com.kuda.app.security.JWTUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/credential")
@RequiredArgsConstructor
public class CredentialController {

	private final OrganizationCredentialService organizationCredentialService;
	private final JWTUtil jwtUtil;

	@PutMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody DefaultSuccessResponse updateCredential(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token,
			@RequestParam(Keys.USERNAME) final String username, @RequestParam(Keys.PASSWORD) final String password)
			throws KudaException {

		organizationCredentialService.update(jwtUtil.getUsername(token), username, password);
		return DefaultSuccessResponse.defaultResponse();
	}
}
