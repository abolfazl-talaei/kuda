package com.kuda.app.dto;


public class NewKudaRequest {

	private String description;
	private Long fromUser;
	private String kudaType;

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public Long getFromUser() {

		return fromUser;
	}

	public void setFromUser(Long fromUser) {

		this.fromUser = fromUser;
	}

	public String getKudaType() {

		return kudaType;
	}

	public void setKudaType(String kudaType) {

		this.kudaType = kudaType;
	}
}
