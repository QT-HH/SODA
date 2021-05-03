package com.tak.soda.controller;

import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.service.MeetingService;
import com.tak.soda.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = {"미팅 컨트롤러"})
@RequestMapping("meeting")
public class MeetingController {
	private final int TokenLength = 15;

	@Autowired
	RandomAccessToken randomAccessToken;
	@Autowired
	MeetingService meetingService;
	@Autowired
	MemberService memberService;
	

	@GetMapping("inviteCode")
	@ApiOperation(value = "미팅 코드", notes = "미팅 코드 유효성", response = String.class)
	private boolean getValidInviteCode(String cidentify) {
		return meetingService.getValidInviteCode(cidentify);
	}


	@GetMapping("attend")
	@ApiOperation(value = "미팅 참여(면접관/면접자 구분)", notes = "면접관인지 면접자인지 구분하는 값 반환")
	public ResponseEntity attendMeeting(String email, String inviteCode) {

		return new ResponseEntity("면접자", HttpStatus.OK);
	}

	@DeleteMapping("del")
	@ApiOperation(value = "미팅 삭제(종료)", notes = "")
	public ResponseEntity delMeeting(String inviteCode) {

		return new ResponseEntity("삭제 성공", HttpStatus.OK);
	}
}
