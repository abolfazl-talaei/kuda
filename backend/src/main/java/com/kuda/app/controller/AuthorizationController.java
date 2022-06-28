package com.kuda.app.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.response.AuthorizationInfo;
import com.kuda.app.dto.response.DefaultSuccessResponse;
import com.kuda.app.dto.response.authorization.OrganizationAuthorizationInfo;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.KudaException;
import com.kuda.app.security.JWTUtil;
import com.kuda.app.service.organization.authorization.AuthorizationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
public class AuthorizationController {

	private final AuthorizationService authorizationService;
	private final JWTUtil jwtUtil;

	@GetMapping
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody List<AuthorizationInfo> getAuthorizations(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws KudaException {

		return authorizationService.gets(jwtUtil.getUsername(token));
	}

	@GetMapping("/permission/available")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody List<AuthorizationInfo> getAvailablePermissions() throws KudaException {

		return authorizationService.getAvailablePermissions();
	}

	@GetMapping("/permission/me")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody List<AuthorizationInfo> getMyPermissions(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token) throws KudaException {

		return authorizationService.getMyPermissions(jwtUtil.getUsername(token));
	}

	@GetMapping("/organization")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody OrganizationAuthorizationInfo getAllOrganizationAuthorizations(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token) throws KudaException {

		return authorizationService.getOrganizationAuthorization(jwtUtil.getUsername(token));
	}

	@DeleteMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody DefaultSuccessResponse deleteAuthorization(@RequestParam(Keys.ID) final Long id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token)
			throws KudaException {

		authorizationService.delete(jwtUtil.getUsername(token), id);
		return DefaultSuccessResponse.defaultResponse();
	}

	@PostMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody DefaultSuccessResponse createPermission(@RequestParam(Keys.USERNAME) final String username,
			@RequestParam(Keys.PERMISSION) final String permission) throws KudaException {

		authorizationService.create(username, permission);
		return DefaultSuccessResponse.defaultResponse();
	}
}
