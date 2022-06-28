package com.kuda.app.enums;

public class KudaRole {

	public static final String ADMIN = "admin";
	public static final String READER = "reader";
	public static final String DEFAULT = "default";

	;

	public static String getUsernamePattern(String role, String organizationUsername) {

		if (KudaRole.DEFAULT.equals(role)) {
			return organizationUsername;
		}
		return organizationUsername + getPattern(role);
	}

	public static String getPattern(String role) {

		return "." + role;
	}
}
