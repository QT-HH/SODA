package com.tak.soda.controller;

import com.tak.soda.domain.Company;
import com.tak.soda.domain.CompanyDto;
import com.tak.soda.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = {"기업 컨트롤러"})
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	CompanyService companyService;
	
	@ApiOperation(value="기업정보 입력", notes="기업 등록")
	@PostMapping("/new")
	public ResponseEntity newCompany(@RequestBody Company company) {
		Long res = companyService.newCompany(company);
		return new ResponseEntity(res, HttpStatus.OK);
	}
	
	@ApiOperation(value="기업정보 전체 리스트", notes="DB에 저장된 기업 전체를 반환")
	@GetMapping("/list")
	public ResponseEntity showAllCompany() {
		List<Company> res = companyService.showCompany();
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
		if(companyService.matchAuthCode(authCode)) {
			return new ResponseEntity("인증완료", HttpStatus.OK);
		}
		return new ResponseEntity("없는코드", HttpStatus.OK);
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
