package com.kuda.app.service.organization.authorization;

import java.util.List;

import com.kuda.app.dto.response.AuthorizationInfo;
import com.kuda.app.dto.response.authorization.OrganizationAuthorizationInfo;
import com.kuda.app.exception.KudaException;
import com.kuda.app.model.Organization;

public interface AuthorizationService {

	void create(String username, String permission) throws KudaException;

	void delete(String username, Long id) throws KudaException;

	List<AuthorizationInfo> gets(String username) throws KudaException;

	OrganizationAuthorizationInfo getOrganizationAuthorization(String username) throws KudaException;

	List<AuthorizationInfo> getAvailablePermissions() throws KudaException;

	void createDefaultOrganizationAuthorizations(Organization organization, String username) throws KudaException;

	List<AuthorizationInfo> getMyPermissions(String username);
}
