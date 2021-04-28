package com.tak.soda.service;

import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tak.soda.repository.MeetingRepository;

@Service
public class MeetingService {

	@Autowired
	MeetingRepository meetingRepository;
	
	public boolean getValidInviteCode(String cidentify) {
		if(meetingRepository.findInviteCode(cidentify) > 0) {
			return true;
		}
		return false;
	}
}
