package com.kuda.app.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuda.app.model.Settings;


public interface SettingsRepository extends JpaRepository<Settings , Long> {

	@Query("select max(settings) from #{#entityName} settings where settings.key = :key and settings.organization.id = :organizationId")
	public Optional<Settings> findOneByKeyAndOrganizationId(@Param("key") String settingKey, @Param("organizationId") Long organizationId);

	@Query("select settings from #{#entityName} settings where settings.tag = :tag and settings.organization.id = :organizationId")
	public List<Settings> findByTagAndOrganizationId(@Param("tag") String tag, @Param("organizationId") Long organizationId);

	@Query("select settings from #{#entityName} settings where settings.organization.id = :organizationId")
	public List<Settings> findByOrganizationId(@Param("organizationId") Long organizationId);
}
