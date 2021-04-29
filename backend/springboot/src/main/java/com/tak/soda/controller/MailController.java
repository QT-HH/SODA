package com.tak.soda.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.function.RejectMail;
import com.tak.soda.model.Company;
import com.tak.soda.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	CompanyService companyService;
	
	@ApiOperation(value="승인", notes="승인 이메일 전송(인증코드 발송)")
	@PostMapping("/approve")
	public ResponseEntity<String> newCompany(@RequestBody Company company) {
		String Token = randomAccessToken.makeToken(TokenLength);
		//company.setCidentify(Token);
		try {
			companyService.modifyCompany(company.getCid(), Token);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		try {
			if(randomAccessToken.sendMail(Token, company.getCemail())) {
				return new ResponseEntity<String>("전송 실패",HttpStatus.OK);
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("전송 완료",HttpStatus.OK);
	}
	
	@ApiOperation(value="거부", notes="거부 이메일 전송")
	@GetMapping("/reject")
	public ResponseEntity<String> reject(String email) {
		try {
			if(rejectMail.sendMail(email)) {
				return new ResponseEntity<String>("전송 실패",HttpStatus.OK);
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("전송 완료",HttpStatus.OK);
	}
}
