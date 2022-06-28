package com.kuda.app.dto.response;


public class UserInfo {

	private Long id;
	private String name;
	private String username;
	private Integer totalPoints;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public Integer getTotalPoints() {

		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {

		this.totalPoints = totalPoints;
	}

	@Override
	public String toString() {

		return "UserInfo [id=" + id + ", name=" + name + ", username=" + username + ", totalPoints=" + totalPoints + "]";
	}
}
