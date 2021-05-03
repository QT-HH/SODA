package com.tak.soda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tak.soda.domain.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{

	@Query(value = "select invite_code from meeting where host_id=:host_id", nativeQuery = true)
	String findInviteCodeByHostId(@Param("host_id") Long host_id);

	@Query(value = "select count(*) from meeting where invite_code=:inviteCode", nativeQuery = true)
	Integer findInviteCode(@Param("inviteCode") String inviteCode);
}
