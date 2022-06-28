package com.kuda.app.repository.dto;


public class IterationUserRank {

	private String username;
	private String name;
	private Long total;
	private Long thanks;
	private Long feedbacks;

	public IterationUserRank() {

		super();
	}

	public IterationUserRank(String username, String name, Long total, Long thanks, Long feedbacks) {

		super();
		this.username = username;
		this.name = name;
		this.total = total;
		this.thanks = thanks;
		this.feedbacks = feedbacks;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Long getTotal() {

		return total;
	}

	public void setTotal(Long total) {

		this.total = total;
	}

	public Long getThanks() {

		return thanks;
	}

	public void setThanks(Long thanks) {

		this.thanks = thanks;
	}

	public Long getFeedbacks() {

		return feedbacks;
	}

	public void setFeedbacks(Long feedbacks) {

		this.feedbacks = feedbacks;
	}

}
