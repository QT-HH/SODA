package com.tak.soda.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.model.Company;
import com.tak.soda.service.CompanyService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class MailController {
	final int TokenLength = 15;
	RandomAccessToken randomAccessToken = new RandomAccessToken();
	@Autowired
	CompanyService companyService;
	
	@ApiOperation(value="회사등록", notes="회사 담당자등록")
	@PostMapping("/newcompany")
	public ResponseEntity newCompany(@RequestBody Company company) {
		String Token = randomAccessToken.makeToken(TokenLength);
		company.setCidentify(Token);
		companyService.newCompany(company);
		try {
			if(randomAccessToken.sendMail(Token, company.getCemail())) {
				return new ResponseEntity("이메일 실패",HttpStatus.OK);
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity("성공",HttpStatus.OK);
	}
}
