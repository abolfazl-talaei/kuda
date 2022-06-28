package com.kuda.app.enums;

import java.util.Arrays;
import java.util.List;

public enum Permissions {

	HOME("home", Arrays.asList(KudaRole.ADMIN, KudaRole.READER, KudaRole.DEFAULT)),
	NEW_KUDA("new.kuda", Arrays.asList(KudaRole.ADMIN, KudaRole.READER, KudaRole.DEFAULT)),
	LIST_KUDA("list.kuda", Arrays.asList(KudaRole.ADMIN, KudaRole.READER, KudaRole.DEFAULT)),
	NEW_USER("new.user", Arrays.asList(KudaRole.ADMIN)),
	LIST_USER("list.user", Arrays.asList(KudaRole.ADMIN, KudaRole.READER, KudaRole.DEFAULT)),
	SETTINGS("settings", Arrays.asList(KudaRole.ADMIN)),
	SETTINGS_KUDA_SHOW("settings.kuda.show", Arrays.asList(KudaRole.ADMIN, KudaRole.READER)),
	LIST_WINNER("list.winner", Arrays.asList(KudaRole.ADMIN, KudaRole.READER, KudaRole.DEFAULT)),

	;

	private Permissions(String name, List<String> defaultRoles) {

		this.name = name;
		this.defaultRoles = defaultRoles;
	}

	private String name;
	private List<String> defaultRoles;

	public static Permissions fromName(String name) {

		for (Permissions item : Permissions.values()) {
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}
		return null;
	}

	public String getName() {

		return name;
	}

	public List<String> getDefaultRoles() {

		return defaultRoles;
	}
}
