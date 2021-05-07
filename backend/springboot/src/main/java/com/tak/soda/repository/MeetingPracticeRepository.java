package com.tak.soda.repository;

import com.tak.soda.domain.MeetingPractice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring Data JPA에서 제공하는 JpaRepository interface를 상속받으면
// @Repository 어노테이션 추가 안해도 됨
public interface MeetingPracticeRepository extends JpaRepository<MeetingPractice, Integer> {
    // SpringBoot JpaRepository 인터페이스가 제공하는 Entity의 기본적인 CRUD 메소드를 사용
}
