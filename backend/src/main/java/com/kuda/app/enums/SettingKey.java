package com.kuda.app.enums;

public enum SettingKey {

	GENERAL("general"),
	DURATION("duration"),
	DEFAULT_KUDA_SHOW("default.kuda.show"),
	FEEDBACK("feedback"),
	FEEDBACK_EFFECT("feedback_effect"),
	TOKEN_LIFETIME_IN_DAYS("token.lifetime.in.days"),

	;

	private SettingKey(String name) {

		this.name = name;
	}

	private String name;

	public String getName() {

		return name;
	}
}
