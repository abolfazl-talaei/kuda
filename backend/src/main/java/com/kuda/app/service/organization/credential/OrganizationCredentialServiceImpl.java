package com.kuda.app.service.organization.credential;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.repository.organization.OrganizationCredentialRepository;
import com.kuda.app.security.SecurityUtil;

@Service
public class OrganizationCredentialServiceImpl implements OrganizationCredentialService {

	private SecurityUtil securityUtil;
	private OrganizationCredentialRepository organizationCredentialRepository;

	public OrganizationCredentialServiceImpl(SecurityUtil securityUtil,
			OrganizationCredentialRepository organizationCredentialRepository) {

		super();
		this.securityUtil = securityUtil;
		this.organizationCredentialRepository = organizationCredentialRepository;
	}

	@Override
	public Optional<OrganizationCredential> findByUsername(String username) {

		return organizationCredentialRepository.findByUsername(username);
	}

	@Override
	public void save(OrganizationCredential instance) {

		organizationCredentialRepository.save(instance);
	}

	@Override
	@Transactional
	public void update(String loggedInUser, String username, String password) throws KudaException {

		if (!securityUtil.getAdminUsername(username).equals(loggedInUser)) {
			throw new KudaException(Errors.ACCESS_DENIED.getName(),
					HttpStatus.FORBIDDEN, Resources.USER.getName(), "605");
		}

		Optional<OrganizationCredential> found = organizationCredentialRepository.findByUsername(username);
		if (!found.isPresent()) {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.FORBIDDEN, Resources.ORGANIZATION_CREDENTIAL.getName(), "2402");
		}

		found.get().setPassword(SecurityUtil.hash(password));
		organizationCredentialRepository.save(found.get());
	}

	@Override
	public Long getOrganizationId(String username) throws KudaException {
		return findByUsername(username)
				.orElseThrow(() -> new KudaException(Errors.NOT_FOUND.getName(),
						HttpStatus.NOT_FOUND, Resources.ORGANIZATION.getName(), "1903"))
				.getOrganization().getId();
	}
}
