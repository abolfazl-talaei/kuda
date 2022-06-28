package com.kuda.app.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuda.app.model.User;


public interface UserRepository extends JpaRepository<User , Long> {

	@Query("select users from #{#entityName} users where users.isActive = true and users.organization.id = :organizationId")
	public List<User> findAllActiveUsersByOrganization(@Param("organizationId") Long organizationId);

	@Query("select user from #{#entityName} user where user.organization.id = :organizationId and user.username = :username")
	public Optional<User> findOneByUsername(@Param("organizationId") Long organizationId, @Param("username") String username);
}
