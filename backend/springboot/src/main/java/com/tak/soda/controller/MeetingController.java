package com.tak.soda.controller;

import com.tak.soda.domain.IntervieweeDto;
import com.tak.soda.domain.MeetingAttendDto;
import com.tak.soda.domain.Member;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.service.MeetingService;
import com.tak.soda.service.MemberService;
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
	private final int TokenLength = 15;

	@Autowired
	MeetingService meetingService;
	@Autowired
	MemberService memberService;
	

	@GetMapping("inviteCode")
	@ApiOperation(value = "미팅 코드", notes = "미팅 코드 유효성", response = String.class)
	private boolean getValidInviteCode(String cidentify) {
		return meetingService.getValidInviteCode(cidentify);
	}


	@GetMapping("interviewee/list")
	@ApiOperation(value = "면접 대상자", notes ="면접자 리스트를 반환")
	public ResponseEntity getIntervieweeList(String inviteCode) {
		List<IntervieweeDto> dto = meetingService.findByInviteCode(inviteCode);

		if(dto.isEmpty()) {
			return new ResponseEntity("면접자 없음", HttpStatus.OK);
		}

		return new ResponseEntity(dto, HttpStatus.OK);
	}


	@GetMapping("attend")
	@ApiOperation(value = "미팅 참여(면접관/면접자 구분)",
			notes = "면접관인지 면접자인지 구분하는 값 반환하고" +
					"면접자라면 status를 한 번 더 확인함" +
					"PLAN : 아직 입장 불가능" +
					"PROGRESS : 입장 가능" +
					"DONE : 미팅 끝남")
	public ResponseEntity attendMeeting(String email, String inviteCode) {

		List<MeetingAttendDto> res = meetingService.findMember(email, inviteCode);

		// 면접관이면?
//		if(meetingService.isInterviewer(member.getU_id())) {
//			//member.setFlag("면접관");
//
//		} // 면접자라면?
//		else{
//			//member.setFlag("면접자");
//		}


		return new ResponseEntity(res, HttpStatus.OK);
	}

	@DeleteMapping("del")
	@ApiOperation(value = "미팅 삭제(종료)", notes = "")
	public ResponseEntity delMeeting(String inviteCode) {
		meetingService.removeMeeting(inviteCode);

		return new ResponseEntity("삭제 성공", HttpStatus.OK);
	}
}
