package com.tak.soda.controller;

import com.tak.soda.domain.dto.IntervieweeDto;
import com.tak.soda.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = {"미팅 컨트롤러"})
@RequestMapping("meeting")
public class MeetingController {
	@Autowired
	MeetingService meetingService;

	@GetMapping("inviteCode")
	@ApiOperation(value = "미팅 코드", notes = "미팅 코드 유효성", response = String.class)
	private boolean getValidInviteCode(String inviteCode) {
		return meetingService.validteInviteCode(inviteCode);
	}

	@GetMapping("interviewee/list")
	@ApiOperation(value = "면접 대상자", notes ="면접자 리스트를 반환")
	public ResponseEntity getIntervieweeList(String inviteCode) {

		List<IntervieweeDto> res = meetingService.findByInviteCode(inviteCode);

		return new ResponseEntity(res, HttpStatus.OK);
	}

	@GetMapping("attend")
	@ApiOperation(value = "미팅 참여(면접관/면접자 구분)",
			notes = "면접관인지 면접자인지 구분하는 값 반환하고\n" +
					"면접자라면 status를 한 번 더 확인함\n" +
					"PLAN : 아직 입장 불가능\n" +
					"PROGRESS : 입장 가능\n" +
					"DONE : 미팅 끝남")
	public ResponseEntity attendMeeting(String email, String inviteCode) {

		if(!meetingService.validateMember(email)) {
			return new ResponseEntity("회원 오류", HttpStatus.OK);
		};

		if(!meetingService.validteInviteCode(inviteCode)) {
			return new ResponseEntity("미팅코드 오류", HttpStatus.OK);
		}


		// 이메일, 미팅코드 일치 확인
		Long u_id = meetingService.enterMeeting(email, inviteCode);
		if(u_id != -1) {
			// 면접관이면?
			if(meetingService.isInterviewer(u_id))  {
				return new ResponseEntity("면접관", HttpStatus.OK);
			}

			// 아니면 면접자
			return new ResponseEntity("면접자", HttpStatus.OK);
		}
		// 면접자라면?
		return new ResponseEntity("초대받지 못함", HttpStatus.OK);
	}

	@DeleteMapping("del")
	@ApiOperation(value = "미팅 삭제(종료)", notes = "")
	public ResponseEntity<String> delMeeting(String inviteCode) {
		meetingService.removeMeeting(inviteCode);

		return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
	}
}
