package com.kuda.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "kuda_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String username;
	private String name;

	@Column(name = "is_active")
	private Boolean isActive;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;

	@Formula(value = "(select count(1) from kuda_kudas kudas where kudas.organization_id = organization_id and kudas.from_user = id and kudas.kuda_type = 'THANKS')")
	private Integer totalPoints;
}
