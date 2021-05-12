package com.tak.soda.service;

import com.tak.soda.domain.Meeting;
import com.tak.soda.domain.MeetingMember;
import com.tak.soda.domain.Member;
import com.tak.soda.domain.MemberStatus;
import com.tak.soda.domain.dto.IntervieweeDto;
import com.tak.soda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

	@Autowired MeetingRepository meetingRepository;
	@Autowired MemberRepository memberRepository;
	@Autowired IntervieweeRepository intervieweeRepository;
	@Autowired MeetingMemberRepository meetingMemberRepository;
	@Autowired MeetingAttendRepository meetingAttendRepository;

	/**
	 * 미팅 코드 유효성 검사
	 * @param inviteCode
	 * @return
	 */
	public boolean validteInviteCode(String inviteCode) {
		if(meetingRepository.findInviteCode(inviteCode) > 0) {
			return true;
		}
		return false;
	}

	public boolean validateMember(String email) {
		List<Member> results = memberRepository.findByEmail(email);
		if(!results.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 면접자 리스트
	 * @param inviteCode
	 */
	public List<IntervieweeDto> findByInviteCode(String inviteCode) {
		return intervieweeRepository.findMemberByInviteCode(inviteCode);
		//return meetingRepository.findByInviteCode(inviteCode);
	}


	/**
	 * 면접방 입장
	 * @param email
	 * @param inviteCode
	 */
	public List<Long> enterMeeting(String email, String inviteCode) {

		List<Member> member = memberRepository.findByEmail(email);
		Meeting meeting = meetingRepository.findByInviteCode(inviteCode);

		List<Long> ids = meetingAttendRepository.findByEmailAndInviteCode(email, inviteCode);

		return ids;
	}

	public boolean isInterviewer(Long u_id) {
		Member member = memberRepository.findById(u_id);

		if(member.getCompany() != null) {
			return true;
		}
		return false;
	}

	public MemberStatus findStatus(Long mm_id) {
		MeetingMember mm = meetingMemberRepository.findById(mm_id);

		return mm.getStatus();
	}

	/**
	 * 면접자 삭제
	 * @param u_id
	 */
	public void removeOne(Long u_id) {
		meetingMemberRepository.deleteByU_Id(u_id);
	}
	
	
	private void removeInterviewee(Long u_id) {
		meetingMemberRepository.deleteById(u_id);
	}

	public void removeAllInterviewees(String inviteCode) {
		List<IntervieweeDto> members = intervieweeRepository.findMemberByInviteCode(inviteCode);

		for(IntervieweeDto member: members) {
			removeInterviewee(member.getMm_id());
		}
	}
}
