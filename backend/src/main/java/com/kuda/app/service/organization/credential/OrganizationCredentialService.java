package com.kuda.app.service.organization.credential;

import java.util.Optional;

import com.kuda.app.exception.KudaException;
import com.kuda.app.model.OrganizationCredential;

public interface OrganizationCredentialService {

	Optional<OrganizationCredential> findByUsername(String username);

	Long getOrganizationId(String username) throws KudaException;

	void save(OrganizationCredential instance);

	void update(String loggedInUser, String username, String password) throws KudaException;
}
