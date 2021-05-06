package com.tak.soda.controller;

import com.tak.soda.domain.*;
import com.tak.soda.domain.dto.InviteDto;
import com.tak.soda.domain.dto.MemberDto;
import com.tak.soda.function.MeetingMail;
import com.tak.soda.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tak.soda.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(tags = {"멤버 컨트롤러"})
@RequestMapping("/member")
public class MemberController {

	private final MeetingMail meetingMail;

	private final MemberService memberService;
	private final CompanyService companyService;

	@PostMapping("/invite")
	@ApiOperation(value="미팅 인증코드 전송", notes="role 통해 면접자/면접관 구분, 면접관이다 면접자이다 적어주면 됨. ex) role='면접관'")
	public ResponseEntity inviteInterviewee(@RequestBody InviteDto dto) throws MessagingException {
		Meeting meeting = memberService.findMeetingByInviteCode(dto.getInviteCode());

		List<String> emails = dto.getEmails();
		List<String> names = dto.getNames();

		for(int i=0; i<dto.getEmails().size(); i++) {
			String email = emails.get(i);
			String name = names.get(i);
			String pattern = "면접자";

			Member member;

			// 멤버가 DB에 없으면
			if (!memberService.isMember(email)) {
				// 면접관/면접자 생성


				if (pattern.matches(dto.getRole())) {
					member = memberService.createInterviewee(email, name);
				} else {
					Company company = companyService.findByName(dto.getCName()).get(0);
					member = memberService.createInterviewer(email, name, company);
				}
			} else {
				member = memberService.findByEmail(email);
			}

			// 미팅방에 초대
			if (pattern.matches(dto.getRole())) {
				memberService.addToMeeting(member, meeting, "면접자");
			} else {
				memberService.addToMeeting(member, meeting, "면접관");
			}
			// 메일 보내기
			meetingMail.sendMail(dto.getInviteCode(), email);

		}

		return new ResponseEntity("전송 완료", HttpStatus.OK);
	}

	@GetMapping("/list")
	@ApiOperation(value="전체 멤버 조회", notes="멤버 리스트 반환")
	public ResponseEntity showAllMember() {
		List<MemberDto> res = memberService.showAll();
		return new ResponseEntity(res, HttpStatus.OK);
	}

	@GetMapping("/search/{uName}")
	@ApiOperation(value="특정 멤버 조회(이름)", notes="멤버 이름으로 검색")
	public ResponseEntity searchByName(@PathVariable("uName")String name) {
		List<MemberDto> res = memberService.findByName(name);

		return new ResponseEntity(res, HttpStatus.OK);
	}

	@PutMapping("/edit/status")
	@ApiOperation(value="멤버 정보 수정(상태)", notes="PLAN: 예정, PROGRESS: 진행, DONE: 완료")
	public ResponseEntity updateMemberStatus(Long u_id, MeetingStatus status) {
		Long saveId = memberService.updateStatus(u_id, status);

		return new ResponseEntity("성공", HttpStatus.OK);
	}
	@PutMapping("/edit/name")
	@ApiOperation(value="멤버 정보 수정(이름)", notes="멤버 이름 변경")
	public ResponseEntity updateMemberName(Long u_id, String u_name) {
		memberService.updateName(u_id, u_name);

		return new ResponseEntity("성공", HttpStatus.OK);
	}

	@PutMapping("/edit/company")
	@ApiOperation(value="멤버 정보 수정(기업)", notes="멤버 기업 이름 변경")
	public ResponseEntity updateMemberCompany(Long u_id, String c_name) {
		memberService.updateCompany(u_id, c_name);

		return new ResponseEntity("성공", HttpStatus.OK);
	}

	// 일단 닫아놓음
//	@PutMapping("/edit/email")
//	@ApiOperation(value="멤버 정보 수정(이메일)", notes="멤버 이메일 변경")
//	public ResponseEntity updateMemberEmail(Long u_id, String email) {
//		memberService.updateEmail(u_id, email);
//
//		return new ResponseEntity("성공", HttpStatus.OK);
//	}

	@DeleteMapping("/del/{id}")
	@ApiOperation(value="멤버 삭제", notes="멤버 id로 삭제")
	public ResponseEntity delMember(@PathVariable("id")Long id) {
		if(memberService.deleteMember(id)) {
			return new ResponseEntity("삭제 성공", HttpStatus.OK);
		}

		return new ResponseEntity("삭제 실패", HttpStatus.OK);
	}
}
