package com.kuda.app.service.organization;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kuda.app.dto.response.LoginInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.repository.organization.OrganizationRepository;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private SecurityUtil securityUtil;
	private OrganizationCredentialService organizationCredentialService;
	private OrganizationRepository organizationRepository;

	public OrganizationServiceImpl(SecurityUtil securityUtil,
			OrganizationCredentialService organizationCredentialService,
			OrganizationRepository organizationRepository) {

		super();
		this.securityUtil = securityUtil;
		this.organizationCredentialService = organizationCredentialService;
		this.organizationRepository = organizationRepository;
	}

	@Override
	public Organization signUp(String name, String username, String password) throws KudaException {

		Optional<OrganizationCredential> existedOrganization = organizationCredentialService.findByUsername(username);
		if (existedOrganization.isPresent()) {
			throw new KudaException(Errors.DUPLICATE.getName(),
					HttpStatus.BAD_REQUEST, Resources.USER.getName(), "602");
		}

		Organization organization = new Organization();
		organization.setIsActive(true);
		organization.setName(name);

		organizationCredentialService.save(new OrganizationCredential(organization,
				securityUtil.getAdminUsername(username), SecurityUtil.hash(password), true));
		organizationCredentialService
				.save(new OrganizationCredential(organization, username,
						SecurityUtil.hash(UUID.randomUUID().toString()), true));
		organizationCredentialService.save(new OrganizationCredential(organization,
				securityUtil.getReaderUsername(username), SecurityUtil.hash(UUID.randomUUID().toString()), true));

		return organizationRepository.save(organization);
	}

	@Override
	public LoginInfo getInfo(String username) throws KudaException {

		if (username == null) {
			throw new KudaException(Errors.INVALID.getName(), HttpStatus.BAD_REQUEST,
					Resources.ORGANIZATION.getName(), "1900");
		}
		Optional<OrganizationCredential> found = organizationCredentialService.findByUsername(username);
		if (!found.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.NOT_FOUND, Resources.ORGANIZATION_CREDENTIAL.getName(), "2400");
		}

		return LoginInfo.builder()
				.token(null).username(SecurityUtil.getOrganizationUsername(found.get().getUsername()))
				.name(found.get().getOrganization().getName())
				.signedIn(false)
				.build();
	}

}
