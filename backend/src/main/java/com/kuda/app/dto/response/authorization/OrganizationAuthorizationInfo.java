package com.kuda.app.dto.response.authorization;


import java.util.List;

import com.kuda.app.dto.response.AuthorizationInfo;


public class OrganizationAuthorizationInfo {

	private List<AuthorizationInfo> defaultAuthorization;

	private List<AuthorizationInfo> readerAuthorization;

	private List<AuthorizationInfo> adminAuthorization;

	public List<AuthorizationInfo> getDefaultAuthorization() {

		return defaultAuthorization;
	}

	public void setDefaultAuthorization(List<AuthorizationInfo> defaultAuthorization) {

		this.defaultAuthorization = defaultAuthorization;
	}

	public List<AuthorizationInfo> getReaderAuthorization() {

		return readerAuthorization;
	}

	public void setReaderAuthorization(List<AuthorizationInfo> readerAuthorization) {

		this.readerAuthorization = readerAuthorization;
	}

	public List<AuthorizationInfo> getAdminAuthorization() {

		return adminAuthorization;
	}

	public void setAdminAuthorization(List<AuthorizationInfo> adminAuthorization) {

		this.adminAuthorization = adminAuthorization;
	}

	@Override
	public String toString() {

		return "OrganizationAuthorizationInfo [defaultAuthorization=" + defaultAuthorization + ", readerAuthorization=" + readerAuthorization + ", adminAuthorization=" + adminAuthorization + "]";
	}
}
