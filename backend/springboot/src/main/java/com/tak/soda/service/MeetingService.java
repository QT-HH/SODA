package com.tak.soda.service;

import com.tak.soda.domain.Meeting;
import com.tak.soda.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tak.soda.repository.MeetingRepository;

import java.time.LocalDateTime;

@Service
public class MeetingService {

	@Autowired
	MeetingRepository meetingRepository;

	public Long createMeeting(Member member, String inviteCode) {
		Meeting meeting = new Meeting();
		String room_name = member.getCompany().getName() +"_"+ inviteCode.substring(0,3);

		meeting.setRoomName(room_name);
		meeting.setInviteCode(inviteCode);
		meeting.setHostId(member.getId());
		meeting.setStartTime(LocalDateTime.now());

		meetingRepository.save(meeting);

		member.addMeeting(meeting);

		return meeting.getId();
	}

	public boolean getValidInviteCode(String cidentify) {
		if(meetingRepository.findInviteCode(cidentify) > 0) {
			return true;
		}
		return false;
	}
}
