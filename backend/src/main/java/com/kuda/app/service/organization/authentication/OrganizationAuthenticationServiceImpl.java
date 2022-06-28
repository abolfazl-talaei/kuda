package com.kuda.app.service.organization.authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kuda.app.dto.response.LoginInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.security.JWTUtil;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
final class OrganizationAuthenticationServiceImpl implements OrganizationAuthenticationService {

	private final OrganizationCredentialService organizationCredentialService;
	private final JWTUtil jwtUtil;

	@Override
	public LoginInfo signIn(final String username, final String password) throws KudaException {

		Optional<OrganizationCredential> foundOrganizationCredential = organizationCredentialService
				.findByUsername(username);
		if (!foundOrganizationCredential.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.BAD_REQUEST, Resources.ORGANIZATION.getName(), "1901");
		}
		if (!SecurityUtil.validate(foundOrganizationCredential.get().getPassword(), password)) {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.BAD_REQUEST, Resources.USERNAME_PASSWORD.getName(), "2700");
		}

		String uuid = jwtUtil.generate(
				generateClaims(username, foundOrganizationCredential.get().getOrganization()),
				username);

		return LoginInfo.builder().token(uuid).build();
	}

	private Map<String, Object> generateClaims(String username, Organization organization) {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("roles", Arrays.asList("ROLE_".concat(SecurityUtil.getUserRole(username))));
		map.put("name", organization.getName());
		return map;
	}

	@Override
	public void signOut(String token) {

		// not implemented yet
	}

	@Override
	public LoginInfo validateTokenStatus(String token) throws KudaException {

		if (!jwtUtil.verify(token)) {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.FORBIDDEN, Resources.TOKEN.getName(), "3000");
		}
		Optional<OrganizationCredential> credentialInfo = organizationCredentialService
				.findByUsername(jwtUtil.getUsername(token));

		String name = credentialInfo.isPresent() ? credentialInfo.get().getOrganization().getName() : null;
		if (credentialInfo.isPresent()) {
			return LoginInfo
					.builder()
					.token(token)
					.username(SecurityUtil.getOrganizationUsername(credentialInfo.get().getUsername()))
					.name(name)
					.signedIn(credentialInfo.isPresent())
					.build();
		} else {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.FORBIDDEN, Resources.TOKEN.getName(), "3001");
		}
	}
}
