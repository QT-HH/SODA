package com.tak.soda.controller;

import com.tak.soda.domain.Company;
import com.tak.soda.domain.CompanyDto;
import com.tak.soda.domain.Member;
import com.tak.soda.domain.MemberDto;
import com.tak.soda.function.ApproveMail;
import com.tak.soda.function.RejectMail;
import com.tak.soda.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = {"기업 컨트롤러"})
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	ApproveMail approveMail;
	@Autowired
	RejectMail rejectMail;

	@Autowired
	CompanyService companyService;
	
	@ApiOperation(value="기업정보 입력", notes="기업 등록하면 기업코드, 미팅코드 이메일로 전송")
	@PostMapping("/new")
	public ResponseEntity newCompany(String cName, String uName, String role, String phone, String email) {
		MemberDto dto = new MemberDto();

		dto.setCName(cName);
		dto.setUName(uName);
		dto.setRole(role);
		dto.setPhone(phone);
		dto.setEmail(email);
		Long res = companyService.newCompany(dto);

		return new ResponseEntity(res, HttpStatus.OK);
	}

	@GetMapping("/email/approve")
	@ApiOperation(value="승인 이메일", notes="기업코드, 미팅코드 같이 보냄")
	public ResponseEntity sendApproveEmail(Long u_id, String email) throws MessagingException {
		String[] val = companyService.generateCode(u_id, email);
		System.out.println(val[0]+" " + val[1]);

		approveMail.sendMail(val[0], val[1], email);

		return new ResponseEntity("메일 보냄", HttpStatus.OK);
	}
	@GetMapping("/email/reject")
	@ApiOperation(value="미승인 이메일", notes="ㅋㅋㅈㅅ")
	public ResponseEntity sendRejectEmail(String email) throws MessagingException {
		rejectMail.sendMail(email);

		return new ResponseEntity("메일 보냄", HttpStatus.OK);

	}


	@ApiOperation(value="기업정보 전체 리스트", notes="DB에 저장된 기업 전체를 반환")
	@GetMapping("/list")
	public ResponseEntity showAllCompany() {
		List<Company> res = companyService.showCompany();
		List<MemberDto> res_list = new ArrayList<>();

		for(Company company: res) {
			Member member = company.getMembers().get(0);
			MemberDto dto = new MemberDto();

			dto.setC_id(company.getId());
			dto.setCName(company.getName());
			dto.setU_id(member.getId());
			dto.setUName(member.getName());
			dto.setRole(member.getRole());
			dto.setPhone(member.getPhone());
			dto.setEmail(member.getEmail());

			res_list.add(dto);
		}

		return new ResponseEntity(res_list, HttpStatus.OK);
	}

	@ApiOperation(value="기업 찾기", notes="기업 이름으로 검색")
	@GetMapping("/find")
	public ResponseEntity findCompany(String cname) {
		List<Company> res = companyService.findByName(cname);
		List<CompanyDto> res_list = new ArrayList<>();

		for(Company company: res) {
			CompanyDto dto = new CompanyDto();

			dto.setId(company.getId());
			dto.setCname(company.getName());
			dto.setAuthCode(company.getAuthCode());

			res_list.add(dto);
		}

		return new ResponseEntity(res_list, HttpStatus.OK);
	}

	@ApiOperation(value = "기업 인증코드 확인", notes = "인증완료/없는코드")
	@PostMapping("/auth")
	public ResponseEntity checkAuthCode(String authCode) {
		Company company = companyService.matchAuthCode(authCode);
		Member member = companyService.findMember(company.getId());
		String[] res;
		String inviteCode = "없는 인증코드";;

		if(company != null) {
			inviteCode = companyService.findInviteCode(member.getId());
			res = new String[] {company.getName(), inviteCode};

			return new ResponseEntity(res, HttpStatus.OK);
		}

		res = new String[] {company.getName(), inviteCode};
		return new ResponseEntity(res, HttpStatus.OK);
	}

	@ApiOperation(value="기업정보 삭제", notes="기업 id로 삭제")
	@DeleteMapping("/del")
	public ResponseEntity delCompany(Long cid) {
		if(companyService.deleteCompany(cid)) {
			return new ResponseEntity("성공",HttpStatus.OK);
		}
		return new ResponseEntity("실패",HttpStatus.OK);
	}
}
