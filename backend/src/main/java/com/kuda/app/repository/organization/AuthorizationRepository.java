package com.kuda.app.repository.organization;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuda.app.model.Authorization;


public interface AuthorizationRepository extends JpaRepository<Authorization , Long> {

	public List<Authorization> findByUsername(String username);
}
