package com.kuda.app.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public class LoginInfo {

	private Boolean signedIn;
	private String name;
	private String username;
	private String token;
	private List<AuthorizationInfo> authorizations;

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public List<AuthorizationInfo> getAuthorizations() {

		return authorizations;
	}

	public void setAuthorizations(List<AuthorizationInfo> authorizations) {

		this.authorizations = authorizations;
	}

	public Boolean getSignedIn() {

		return signedIn;
	}

	public void setSignedIn(Boolean signedIn) {

		this.signedIn = signedIn;
	}

	public String getToken() {

		return token;
	}

	public void setToken(String token) {

		this.token = token;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String toString() {

		return "LoginInfo [signedIn=" + signedIn + ", name=" + name + ", token=" + token + ", authorizations="
				+ authorizations + "]";
	}
}
