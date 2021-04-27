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
	
	public String getValidInviteCode() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		String generatedString;
		
		// 무한루프는 그대로지만 HashSet 활용해서 DB 접근 최소화!
		HashSet<String> set = new HashSet<>(meetingRepository.findAllCode());
		while (true) {
			generatedString = random.ints(leftLimit,rightLimit + 1)
					  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
					  .limit(targetStringLength)
					  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					  .toString();
			
			if(!set.contains(generatedString)) break;
		}
		
		return generatedString;
	}
}
