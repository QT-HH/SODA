package com.tak.soda.service;

import com.tak.soda.domain.IntervieweeDto;
import com.tak.soda.domain.Meeting;
import com.tak.soda.domain.MeetingMember;
import com.tak.soda.domain.Member;
import com.tak.soda.repository.InterVieweeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tak.soda.repository.MeetingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {

	@Autowired
	MeetingRepository meetingRepository;
	@Autowired
	InterVieweeRepository interVieweeRepository;

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

	/**
	 * 면접방 입장
	 * @param email
	 * @param inviteCode
	 */
	public void enterMeeting(String email, String inviteCode) {

	}

	public List<IntervieweeDto> findByInviteCode(String inviteCode) {
		List<IntervieweeDto> meetingMember = interVieweeRepository.findMemberByInviteCode(inviteCode);
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
