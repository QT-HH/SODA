package com.tak.soda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tak.soda.service.MeetingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = {"미팅 컨트롤러"})
@RequestMapping("meeting")
public class MeetingController {
	@Autowired
	MeetingService meetingService;
	

	@GetMapping("inviteCode")
	@ApiOperation(value = "미팅 코드", notes = "미팅 코드 유효성", response = String.class)
	private boolean getValidInviteCode(String cidentify) {
		return meetingService.getValidInviteCode(cidentify);
	}
	
	
}
