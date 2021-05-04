package com.tak.soda.service;

import com.tak.soda.domain.IntervieweeDto;
import com.tak.soda.domain.MeetingAttendDto;
import com.tak.soda.domain.Member;
import com.tak.soda.repository.JoinSQLRepository;
import com.tak.soda.repository.MeetingRepository;
import com.tak.soda.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

	@Autowired
	MeetingRepository meetingRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	JoinSQLRepository joinSQLRepository;

	/**
	 * 미팅 코드 유효성 검사
	 * @param inviteCode
	 * @return
	 */
	public boolean getValidInviteCode(String inviteCode) {
		if(meetingRepository.findInviteCode(inviteCode) > 0) {
			return true;
		}
		return false;
	}

	public List<MeetingAttendDto> findMember(String email, String inviteCode) {
		List<MeetingAttendDto> res = joinSQLRepository.findByEmailAndInviteCode(email, inviteCode);

		return res;
	}

	public boolean isInterviewer(Long u_id) {

		return true;
	}
	/**
	 * 면접방 입장
	 * @param email
	 * @param inviteCode
	 */
	public void enterMeeting(String email, String inviteCode) {

	}

	/**
	 * 면접자 리스트
	 * @param inviteCode
	 */
	public List<IntervieweeDto> findByInviteCode(String inviteCode) {
		List<IntervieweeDto> meetingMember = joinSQLRepository.findMemberByInviteCode(inviteCode);

		if(meetingMember.isEmpty()) {
			System.out.println("비어있음");
		}

		return meetingMember;
	}

	/**
	 * 면접방 폭파
	 * @param inviteCode
	 */
	public long removeMeeting(String inviteCode) {
		long id = meetingRepository.deleteByInviteCode(inviteCode);

		return id;
	}
}
