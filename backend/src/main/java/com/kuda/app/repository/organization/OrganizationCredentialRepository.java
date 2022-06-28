package com.kuda.app.repository.organization;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuda.app.model.OrganizationCredential;


public interface OrganizationCredentialRepository extends JpaRepository<OrganizationCredential , Long> {

	@Query("select organizationCredential from #{#entityName} organizationCredential where organizationCredential.isActive = true and organizationCredential.username = :username")
	public Optional<OrganizationCredential> findByUsername(@Param("username") String username);
}
