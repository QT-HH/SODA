package com.tak.soda.controller;

import com.tak.soda.SodaApplication;
import com.tak.soda.domain.Company;
import com.tak.soda.domain.dto.CompanyDto;
import com.tak.soda.domain.Member;
import com.tak.soda.domain.dto.MemberDto;
import com.tak.soda.function.Mail;
import com.tak.soda.function.SodaEmail;
import com.tak.soda.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = {"기업 컨트롤러"})
@RequestMapping("/company")
public class CompanyController {

	private static Logger log = LoggerFactory.getLogger(SodaApplication.class);

	@Autowired SodaEmail sodaEmail;
	@Autowired CompanyService companyService;
	
	@ApiOperation(value="기업정보 입력", notes="기업 등록하면 기업코드, 미팅코드 이메일로 전송")
	@PostMapping("/new")
	public ResponseEntity<Long> newCompany(String cName, String uName, String role, String phone, String email) {
		MemberDto dto = new MemberDto();

		dto.setCName(cName);
		dto.setUName(uName);
		dto.setRole(role);
		dto.setPhone(phone);
		dto.setEmail(email);
		Long res = companyService.newCompany(dto);

		return new ResponseEntity<Long>(res, HttpStatus.OK);
	}

	@GetMapping(value = "/email/approve")
	@ApiOperation(value="승인 이메일", notes="기업코드, 미팅코드 같이 보냄")
	public ResponseEntity<String> sendApproveEmail(Long u_id, String email) throws MessagingException, IOException {
		if (companyService.checkAuthCode(u_id)) {
			return new ResponseEntity<String>("승인됨", HttpStatus.OK);
		}

		String[] val = companyService.generateCode(u_id, email);

		//String[] val = {"abcasdfasdfasdf", "123123123123123"};

		log.info("Sending Approve Email to uId: " + u_id);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("authCode", val[0]);
		properties.put("inviteCode", val[1]);
		properties.put("sign", "소리를 보다");

		Mail mail = Mail.builder()
				.from("no-reply@soda.com")
				.to(email)
				.htmlTemplate(new Mail.HtmlTemplate("approve", properties))
				.subject("[SODA 관리자] 기업코드 요청 관련 안내")
				.build();

		sodaEmail.sendMail(mail);

		return new ResponseEntity<String>("메일 보냄", HttpStatus.OK);
	}
	@GetMapping(value = "/email/reject")
	@ApiOperation(value="미승인 이메일", notes="ㅋㅋㅈㅅ")
	public ResponseEntity<String> sendRejectEmail(String email) throws MessagingException, IOException {
		log.info("Sending Reject Email with Thymeleaf HTML Template Example");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("name", "SODA admin");
		properties.put("location", "South Korea");
		properties.put("sign", "소리를 보다");

		Mail mail = Mail.builder()
				.from("no-reply@soda.com")
				.to(email)
				.htmlTemplate(new Mail.HtmlTemplate("reject", properties))
				.subject("[SODA 관리자] 기업코드 요청 관련 안내")
				.build();

		sodaEmail.sendMail(mail);

		return new ResponseEntity<String>("메일 보냄", HttpStatus.OK);

	}

	@ApiOperation(value="기업정보 전체 리스트", notes="DB에 저장된 기업 전체를 반환")
	@GetMapping("/list")
	public ResponseEntity<List<MemberDto>> showAllCompany() {
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

		return new ResponseEntity<List<MemberDto>>(res_list, HttpStatus.OK);
	}

	@ApiOperation(value="기업 찾기", notes="기업 이름으로 검색")
	@GetMapping("/find")
	public ResponseEntity<List<CompanyDto>> findCompany(String cname) {
		List<Company> res = companyService.findByName(cname);
		List<CompanyDto> res_list = new ArrayList<>();

		for(Company company: res) {
			CompanyDto dto = new CompanyDto();

			dto.setId(company.getId());
			dto.setCname(company.getName());
			dto.setAuthCode(company.getAuthCode());

			res_list.add(dto);
		}

		return new ResponseEntity<List<CompanyDto>>(res_list, HttpStatus.OK);
	}

	@ApiOperation(value = "기업 인증코드 확인", notes = "인증완료/없는코드")
	@PostMapping(value = "/auth")
	public ResponseEntity<String[]> checkAuthCode(String authCode) {
		String[] res;
		String inviteCode = "없는 인증코드";

		Company company = companyService.matchAuthCode(authCode);
		// 기업이 없으면
		if(company == null) {
			res = new String[] {"", "", inviteCode};
			return new ResponseEntity<String[]>(res, HttpStatus.OK);
		}

		// 담당자 찾기
		String[] tmp = companyService.findHost(company.getId()).split(",");

		res = new String[] { tmp[0], company.getName(), tmp[1]};
		return new ResponseEntity<String[]>(res, HttpStatus.OK);
	}

	@ApiOperation(value="기업정보 삭제", notes="기업 id로 삭제")
	@DeleteMapping(value = "/del")
	public ResponseEntity<String> delCompany(Long cid) {
		if(companyService.deleteCompany(cid)) {
			return new ResponseEntity<String>("성공",HttpStatus.OK);
		}
		return new ResponseEntity<String>("실패",HttpStatus.OK);
	}
}
