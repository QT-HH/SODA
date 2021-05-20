package com.tak.soda.service;

import com.tak.soda.domain.MemberStatus;
import com.tak.soda.domain.dto.IntervieweeDto;
import com.tak.soda.repository.IntervieweeRepository;
import com.tak.soda.repository.MeetingAttendRepository;
import com.tak.soda.repository.MeetingRepository;
import com.tak.soda.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MeetingServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MeetingRepository meetingRepository;
    @Autowired MeetingAttendRepository meetingAttendRepository;
    @Autowired IntervieweeRepository intervieweeRepository;

    @Autowired MeetingService meetingService;

    private String inviteCode = "Ch7H6hegiiH9aiA";
    private String email = "hyunheal@naver.com";

    @DisplayName("1. 미팅코드 유효성 검사")
    @Test
    void testValidateInviteCode() {
        //Given
        String inviteCodeFail = "abcd";

        //When
        boolean success = meetingService.validteInviteCode(this.inviteCode);
        boolean fail = meetingService.validteInviteCode(inviteCodeFail);

        //Then
        assertEquals(true, success);
        assertEquals(false, fail);
    }

    @DisplayName("2. 멤버 유효성 검사")
    @Test
    void testValidateMember() {
        //Given
        String invalidEmail = "test@test.com";

        //When
        boolean success = meetingService.validateMember(this.email);
        boolean fail = meetingService.validateMember(invalidEmail);

        //Then
        assertEquals(true, success);
        assertEquals(false, fail);
    }

    @DisplayName("3. 면접자 리스트(미팅코드)")
    @Test
    void testFindByInviteCode() {
        //Given

        //When
        List<IntervieweeDto> results = meetingService.findByInviteCode(this.inviteCode);
        List<IntervieweeDto> expect = intervieweeRepository.findMemberByInviteCode(this.inviteCode);
        Long u_id = expect == null ? 0L : expect.get(0).getU_id();

        //Then
        for(IntervieweeDto row: results) {
            System.out.println(row.toString());
        }
        assertEquals(4, u_id);
    }

    @DisplayName("4. 멤버방 입장 테스트(이메일, 인증코드 확인)")
    @Test
    void testEnterMeeting() {
        //Given

        //When
        List<Long> results = meetingService.enterMeeting(this.email, this.inviteCode);

        //Then
        assertEquals(results, meetingAttendRepository.findByEmailAndInviteCode(this.email, this.inviteCode));
    }

    @DisplayName("5. 면접자인지 확인")
    @Test
    void testIsInterviewer() {
        //Given
        Long interviewerUId = 2L;
        Long intervieweeUId = 4L;

        //When
        boolean success = meetingService.isInterviewer(interviewerUId);
        boolean fail = meetingService.isInterviewer(intervieweeUId);

        //Then
        assertEquals(true, success);
        assertEquals(false, fail);
    }


    @DisplayName("6. 상태 확인(mm_id)")
    @Test
    void testFindStatus() {
        //Given
        Long mm_id = 3L;
        MemberStatus expect = MemberStatus.PROGRESS;

        //When
        MemberStatus result = meetingService.findStatus(mm_id);

        //Then
        assertEquals(expect, result);
    }
//    @DisplayName("7. 면접자 삭제(1명)")
//    @Test
//    void testRemoveInterviewee() {
//        //Given
//
//        //When
//
//        //Then
//    }
//
//
//    @DisplayName("8. 면접자 삭제(전체)")
//    @Test
//    void testRemoveAllInterviewees() {
//        //Given
//
//        //When
//
//        //Then
//    }
}