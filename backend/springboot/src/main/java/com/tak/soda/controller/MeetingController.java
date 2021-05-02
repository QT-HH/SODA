package com.tak.soda.controller;

import com.tak.soda.domain.MeetingDto;
import com.tak.soda.domain.Member;
import com.tak.soda.domain.MemberDto;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tak.soda.service.MeetingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.mail.MessagingException;
import java.util.List;

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
	
	@PostMapping("new")
	@ApiOperation(value = "새로운 미팅", notes="미팅 생성하고 승인 이메일 전송(인증코드 발송)")
	public ResponseEntity createMeeting(@RequestBody MemberDto dto) {
		Member member = memberService.findById(dto.getId());
		String Token = randomAccessToken.makeToken(TokenLength);

		meetingService.createMeeting(member, Token);

		return new ResponseEntity<String>("전송 완료",HttpStatus.OK);
	}

	@PostMapping("invite/interviewer")
	@ApiOperation(value = "면접관 미팅 초대")
	public ResponseEntity inviteInterviewer(@RequestBody MeetingDto dto) {

		return new ResponseEntity("초대 성공", HttpStatus.OK);
	}

	@PostMapping("invite/interviewee")
	@ApiOperation(value = "면접자 미팅 초대")
	public ResponseEntity inviteInterviewee(@RequestBody MeetingDto dto) {

		return new ResponseEntity("초대 성공", HttpStatus.OK);
	}
}
