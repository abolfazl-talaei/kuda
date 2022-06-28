package com.kuda.app.service.organization.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuda.app.dto.response.AuthorizationInfo;
import com.kuda.app.dto.response.authorization.OrganizationAuthorizationInfo;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.enums.Permissions;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.mapper.AuthorizationMapper;
import com.kuda.app.model.Authorization;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.repository.organization.AuthorizationRepository;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	private SecurityUtil securityUtil;
	private AuthorizationRepository authorizationRepository;
	private OrganizationCredentialService organizationCredentialService;

	public AuthorizationServiceImpl(SecurityUtil securityUtil, AuthorizationRepository authorizationRepository,
			OrganizationCredentialService organizationCredentialService) {

		super();
		this.securityUtil = securityUtil;
		this.authorizationRepository = authorizationRepository;
		this.organizationCredentialService = organizationCredentialService;
	}

	@Override
	@Transactional
	public void create(String username, String permission) throws KudaException {

		String organizationUsername = SecurityUtil.getOrganizationUsername(username);

		if (!organizationUsername.equalsIgnoreCase(SecurityUtil.getOrganizationUsername(username))) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.FORBIDDEN, Resources.ORGANIZATION.getName(), "1906");
		}

		Optional<OrganizationCredential> organization = organizationCredentialService
				.findByUsername(organizationUsername);
		if (!organization.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.BAD_REQUEST, Resources.ORGANIZATION.getName(), "1902");
		}

		createAuthorizationInstance(username, permission, organization.get().getOrganization());
	}

	@Override
	public void createDefaultOrganizationAuthorizations(Organization organization, String username)
			throws KudaException {

		for (Permissions permission : Permissions.values()) {
			for (String role : permission.getDefaultRoles()) {
				createAuthorizationInstance(KudaRole.getUsernamePattern(role, username), permission.getName(),
						organization);
			}
		}
	}

	private void createAuthorizationInstance(String username, String permission, Organization organization) {

		Authorization authorization = new Authorization();
		authorization.setUsername(username);
		authorization.setPermission(permission);
		authorization.setOrganization(organization);

		authorizationRepository.save(authorization);
	}

	@Override
	@Transactional
	public void delete(String username, Long id) throws KudaException {

		String organizationUsername = SecurityUtil.getOrganizationUsername(username);

		Optional<OrganizationCredential> organizationCredential = organizationCredentialService
				.findByUsername(organizationUsername);
		if (!organizationCredential.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.NOT_FOUND, Resources.ORGANIZATION_CREDENTIAL.getName(), "2401");
		}

		Optional<Authorization> foundAuthorization = authorizationRepository.findById(id);

		if (!foundAuthorization.isPresent()) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.FORBIDDEN, Resources.ORGANIZATION_CREDENTIAL.getName(), "2403");
		}
		if (foundAuthorization.get().getOrganization().getId() != organizationCredential.get().getOrganization()
				.getId()) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.FORBIDDEN, Resources.ORGANIZATION_CREDENTIAL.getName(), "2404");
		}

		authorizationRepository.delete(foundAuthorization.get());
	}

	@Override
	public List<AuthorizationInfo> gets(String username) throws KudaException {

		String organizationUsername = SecurityUtil.getOrganizationUsername(username);

		if (!organizationUsername.equalsIgnoreCase(SecurityUtil.getOrganizationUsername(username))) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.FORBIDDEN, Resources.ORGANIZATION_CREDENTIAL.getName(), "2405");
		}

		return authorizationRepository.findByUsername(username).stream().map(AuthorizationMapper::map)
				.collect(Collectors.toList());
	}

	@Override
	public OrganizationAuthorizationInfo getOrganizationAuthorization(String username) throws KudaException {

		OrganizationAuthorizationInfo organizationAuthorizationInfo = new OrganizationAuthorizationInfo();

		organizationAuthorizationInfo.setAdminAuthorization(gets(securityUtil.getAdminUsername(username)));
		organizationAuthorizationInfo.setReaderAuthorization(gets(securityUtil.getReaderUsername(username)));
		organizationAuthorizationInfo.setDefaultAuthorization(gets(securityUtil.getDefaultUsername(username)));

		return organizationAuthorizationInfo;
	}

	@Override
	public List<AuthorizationInfo> getAvailablePermissions() throws KudaException {

		List<AuthorizationInfo> permisisons = new ArrayList<>();
		for (Permissions permission : Permissions.values()) {
			permisisons.add(new AuthorizationInfo(null, permission.getName()));
		}
		return permisisons;
	}

	@Override
	public List<AuthorizationInfo> getMyPermissions(String username) {

		return authorizationRepository.findByUsername(username).stream().map(AuthorizationMapper::map)
				.collect(Collectors.toList());
	}

}
