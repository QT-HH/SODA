package com.tak.soda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tak.soda.domain.Company;
import com.tak.soda.domain.CompanyDto;
import com.tak.soda.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = {"기업 컨트롤러"})
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@ApiOperation(value="기업정보 입력", notes="기업 정보 입력 후 인증코드 요청")
	@PostMapping("/new-company")
	public ResponseEntity newCompany(@RequestBody CompanyDto company) {
		System.out.println(company.toString());
		
		
		try {
		} catch (Exception e) {
			System.out.println("이메일 중복?");
			return new ResponseEntity("이메일 중복", HttpStatus.OK);
		}
		return new ResponseEntity("인증요청", HttpStatus.OK);
	}
	
	@ApiOperation(value="기업정보 리스트", notes="인증코드 요청한 기업 리스트")
	@GetMapping("/company-list")
	public ResponseEntity<String> showCompany() {
		List<Company> res = companyService.showCompany();
		
		return new ResponseEntity(res, HttpStatus.OK);
	}
	
	@ApiOperation(value="기업정보 삭제", notes="승인 취소")
	@DeleteMapping("/del")
	public ResponseEntity<String> deltCompany(Long cid) {
		if(companyService.deleteCompany(cid)) {
			return new ResponseEntity<String>("성공",HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("실패",HttpStatus.OK);
	}
}
