package com.kuda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuda.app.model.Kuda;
import com.kuda.app.repository.dto.IterationUserRank;

public interface KudaRepository extends JpaRepository<Kuda, Long> {

	public static final String DURATION_SETTINGS_NAME = "'duration'";

	@Query("select kuda from #{#entityName} kuda, Settings settings where settings.key = " + DURATION_SETTINGS_NAME
			+ " and settings.value = kuda.duration and kuda.showStatus = :showStatus and kuda.organization.id = :organizationId order by kuda.creationTime desc")
	public List<Kuda> findByOrganizationAndShowStatus(@Param("organizationId") Long organizationId,
			@Param("showStatus") Boolean show);

	@Query("select new com.kuda.app.repository.dto.IterationUserRank(kuda.fromUser.username, kuda.fromUser.name, sum(case when kuda.kudaType = 'THANKS' then 1 else -1 end),"
			+ "sum(case when kuda.kudaType = 'THANKS' then 1 else 0 end), sum(case when kuda.kudaType = 'THANKS' then 0 else 1 end)) from #{#entityName} kuda, "
			+ "Settings settings where settings.key = " + DURATION_SETTINGS_NAME + " and settings.value = "
			+ "kuda.duration and kuda.showStatus = :showStatus and kuda.readStatus = true and kuda.organization.id = :organizationId group by kuda.fromUser.username, kuda.fromUser.name order by sum(case when kuda.kudaType = 'THANKS' then 1 else -1 end) desc")
	public List<IterationUserRank> findReadKudas(@Param("organizationId") Long organizationId,
			@Param("showStatus") Boolean show);

	@Query("select new com.kuda.app.repository.dto.IterationUserRank(kuda.fromUser.username, kuda.fromUser.name, sum(case when kuda.kudaType = 'THANKS' then 1 else -1 end),"
			+ "sum(case when kuda.kudaType = 'THANKS' then 1 else 0 end), sum(case when kuda.kudaType = 'THANKS' then 0 else 1 end)) from #{#entityName} kuda "
			+ "where kuda.duration = :duration"
			+ " and kuda.showStatus = true and kuda.readStatus = true and kuda.organization.id = :organizationId group by kuda.fromUser.username, kuda.fromUser.name order by sum(case when kuda.kudaType = 'THANKS' then 1 else -1 end) desc")
	public List<IterationUserRank> findDurationKudas(@Param("organizationId") Long organizationId,
			@Param("duration") Integer duration);

	@Query("select kuda from #{#entityName} kuda, Settings settings where settings.key = " + DURATION_SETTINGS_NAME
			+ " and settings.value = kuda.duration and kuda.organization.id = :organizationId and (:feedbackEffect = true or (:feedbackEffect = false and kuda.kudaType <> 'FEEDBACK'))")
	public List<Kuda> findAllDurationKudas(@Param("organizationId") Long organizationId,
			@Param("feedbackEffect") Boolean feedbackEffect);

	@Query("select count(kuda) from #{#entityName} kuda, Settings settings where settings.key = "
			+ DURATION_SETTINGS_NAME
			+ " and settings.value = kuda.duration and kuda.readStatus = true and kuda.organization.id = :organizationId")
	public Long findAllReadDurationKudas(@Param("organizationId") Long organizationId);
}
