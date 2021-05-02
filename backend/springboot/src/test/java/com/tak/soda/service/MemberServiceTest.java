package com.tak.soda.service;

import com.tak.soda.domain.*;
import com.tak.soda.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("신규 멤버 등록")
    void testJoin() {
        //Given
        MemberDto info = new MemberDto("삼성전자", "싸피", "부장","010-1111-2222", "ssafy@ssafy.com");

        //When
        Long id = memberService.join(info);


        //Then
        assertEquals(id, memberRepository.findById(id).getId());
    }

    @Test
    @DisplayName("멤버 정보 수정 - status 변경")
    void testUpdateStatus() {
        //Given
        Member member = memberRepository.findAll().get(0);
        MemberStatus origin = member.getStatus();
        MemberStatus newStatus = MemberStatus.DONE;

        //When


        //Then


    }

    @Test
    @DisplayName("멤버 정보 수정 - 회사 변경")
    void testUpdateCompany() {
        //Given

        //When

        //Then

    }

    @Test
    @DisplayName("멤버 정보 수정 - email 변경")
    void testUpdateEmail() {
        //Given

        //When

        //Then

    }

    @Test
    @DisplayName("멤버 검색(이름)")
    void testSearchByName() {
        //Given

        //When

        //Then

    }

    @Test
    @DisplayName("멤버 삭제")
    void testDeleteMember() {
        //Given

        //When

        //Then

    }
}