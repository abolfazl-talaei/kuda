package com.kuda.app.model;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "kuda_organization_credential")
public class OrganizationCredential implements UserDetails {

	private static final long serialVersionUID = -3654630858959613224L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;

	private String username;
	private String password;

	@Column(name = "is_active")
	private Boolean isActive;

	public OrganizationCredential() {

		super();
	}

	public OrganizationCredential(Organization organization, String username, String password, Boolean isActive) {

		super();
		this.organization = organization;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
	}

	@JsonIgnore
	@Override
	public String getPassword() {

		return password;
	}

	@Transient
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.emptyList();
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@JsonIgnore
	@Transient
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {

		return true;
	}
}
