package com.kuda.app.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthorizationInfo {

	private String username;
	private String permission;
	private Long id;

	public AuthorizationInfo(Long id, String permission) {

		this.setId(id);
		this.setPermission(permission);
	}

	public AuthorizationInfo(Long id, String username, String permission) {

		this.setId(id);
		this.setUsername(username);
		this.setPermission(permission);
	}
}
