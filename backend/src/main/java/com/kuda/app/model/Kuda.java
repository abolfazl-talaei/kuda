package com.kuda.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuda.app.enums.KudaType;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "kuda_kudas")
public class Kuda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Organization organization;

	private String description;

	@JoinColumn(name = "from_user")
	@ManyToOne(fetch = FetchType.LAZY)
	private User fromUser;

	@Column(name = "show_status")
	private Boolean showStatus;

	@Column(name = "read_status")
	private Boolean readStatus;

	@Column(name = "creation_time")
	private Date creationTime;

	@Column(name = "request_id")
	private String requestId;

	private Integer duration;

	@Column(name = "kuda_type")
	@Enumerated(EnumType.STRING)
	private KudaType kudaType;

	public Kuda() {

		super();
		setCreationTime(new Date());
	}

	@JsonIgnore
	public Organization getOrganization() {

		return organization;
	}
}
