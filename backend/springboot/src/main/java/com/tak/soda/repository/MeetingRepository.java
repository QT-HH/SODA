package com.tak.soda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tak.soda.domain.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{

	@Query(value = "select count(*) from companys where cidentify = :cidentify", nativeQuery = true)
	Integer findInviteCode(@Param("cidentify") String cidentify);
}
