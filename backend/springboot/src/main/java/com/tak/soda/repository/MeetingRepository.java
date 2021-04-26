package com.tak.soda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tak.soda.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{

	@Query(value = "select invite_code from meeting", nativeQuery = true)
	List<String> findAllCode();
}
