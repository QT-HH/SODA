package com.tak.soda.repository;

import com.tak.soda.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long>{
	@Query(value = "select count(*) from meeting where invite_code=:inviteCode", nativeQuery = true)
	Integer findInviteCode(@Param("inviteCode") String inviteCode);

	Meeting findByHostId(Long host_id);
	Meeting findByInviteCode(String inviteCode);
}
