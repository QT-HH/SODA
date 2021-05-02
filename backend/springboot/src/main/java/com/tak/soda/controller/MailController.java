package com.tak.soda.controller;

import javax.mail.MessagingException;

import com.tak.soda.domain.Meeting;
import com.tak.soda.domain.MemberDto;
import com.tak.soda.service.MeetingService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tak.soda.domain.Member;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.function.RejectMail;
import com.tak.soda.service.CompanyService;
import com.tak.soda.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = {"이메일 컨트롤러"})
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/email")
public class MailController {
	final int TokenLength = 15;
	@Autowired
	RandomAccessToken randomAccessToken;
	@Autowired
	RejectMail rejectMail;
	@Autowired
	MeetingService meetingService;
	@Autowired
	MemberService memberService;
	
	@ApiOperation(value="승인", notes="승인 이메일 전송(인증코드 발송)")
	@PostMapping("/approve")
	public ResponseEntity<String> approve(@RequestBody MemberDto dto) {
		Member member = memberService.findById(dto.getId());
		String Token = randomAccessToken.makeToken(TokenLength);

		meetingService.createMeeting(member, Token);
		try {
			if(randomAccessToken.sendMail(Token, member.getEmail())) {
				return new ResponseEntity<String>("전송 실패",HttpStatus.OK);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("전송 완료",HttpStatus.OK);
	}
	
	@ApiOperation(value="거부", notes="거부 이메일 전송")
	@PostMapping("/reject")
	public ResponseEntity<String> reject(@RequestBody MemberDto member) {
		try {
			if(rejectMail.sendMail(member.getEmail())) {
				return new ResponseEntity<String>("전송 실패",HttpStatus.OK);
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("전송 완료",HttpStatus.OK);
	}
}
