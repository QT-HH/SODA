package com.tak.soda.controller;

import com.tak.soda.domain.Company;
import com.tak.soda.domain.CompanyDto;
import com.tak.soda.domain.MemberDto;
import com.tak.soda.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tak.soda.domain.Member;
import com.tak.soda.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(tags = {"멤버 컨트롤러"})
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	private final CompanyService companyService;

	@PostMapping("/new")
	@ApiOperation(value="멤버 등록", notes="새로운 멤버 등록(기업정보, 사용자정보)")
	public ResponseEntity newMember(@RequestBody CompanyDto dto) {
		Long saveId = memberService.join(dto);

		return new ResponseEntity(saveId, HttpStatus.OK);
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
	public ResponseEntity updateMemberStatus(@RequestBody MemberDto member) {
		Long saveId = memberService.updateStatus(member);

		return new ResponseEntity("성공", HttpStatus.OK);
	}

	@PutMapping("/edit/company")
	@ApiOperation(value="멤버 정보 수정(기업)", notes="멤버 기업 이름 변경")
	public ResponseEntity updateMemberCompany(@RequestBody MemberDto member) {
		memberService.updateCompany(member);

		return new ResponseEntity("성공", HttpStatus.OK);
	}

	@PutMapping("/edit/email")
	@ApiOperation(value="멤버 정보 수정(이메일)", notes="멤버 이메일 변경")
	public ResponseEntity updateMemberEmail(@RequestBody MemberDto member) {
		memberService.updateEmail(member);

		return new ResponseEntity("성공", HttpStatus.OK);
	}

	@DeleteMapping("/del/{id}")
	@ApiOperation(value="멤버 삭제", notes="멤버 id로 삭제")
	public ResponseEntity delMember(@PathVariable("id")Long id) {
		if(memberService.deleteMember(id)) {
			return new ResponseEntity("삭제 성공", HttpStatus.OK);
		}

		return new ResponseEntity("삭제 실패", HttpStatus.OK);
	}
}
