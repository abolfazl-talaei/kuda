package com.kuda.app.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.response.LoginInfo;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.KudaException;
import com.kuda.app.model.Organization;
import com.kuda.app.service.organization.OrganizationService;
import com.kuda.app.service.organization.authentication.OrganizationAuthenticationService;
import com.kuda.app.service.organization.authorization.AuthorizationService;
import com.kuda.app.service.setting.SettingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	public final OrganizationAuthenticationService authentication;
	public final OrganizationService organizationService;
	public final AuthorizationService authorizationService;
	public final SettingService settingService;

	@PostMapping("/signin")
	public @ResponseBody LoginInfo signIn(@RequestParam(Keys.USERNAME) final String username,
			@RequestParam(Keys.PASSWORD) final String password) throws KudaException {

		return authentication.signIn(username, password);
	}

	@GetMapping
	public @ResponseBody LoginInfo getInfo(@RequestParam(Keys.USERNAME) final String username) throws Exception {

		return organizationService.getInfo(username);
	}

	@PostMapping("/signup")
	@Transactional
	public @ResponseBody LoginInfo signUp(@RequestParam(Keys.NAME) final String name,
			@RequestParam(Keys.USERNAME) final String username, @RequestParam(Keys.PASSWORD) final String password)
			throws Exception {

		Organization organization = organizationService.signUp(name, username, password);
		settingService.initOrganizationSettings(organization);
		authorizationService.createDefaultOrganizationAuthorizations(organization, username);
		return signIn(username.concat(".").concat(KudaRole.ADMIN), password);
	}

	@PostMapping("/signout")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody void signOut(@RequestParam(Keys.TOKEN) final String token) {

		authentication.signOut(token);
	}

	@PostMapping("/status/validate")
	public @ResponseBody LoginInfo validateLoginStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token)
			throws KudaException {

		return authentication.validateTokenStatus(token);
	}
}
