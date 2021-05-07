package com.tak.soda.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class RejectMail {
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendMail(String email) throws javax.mail.MessagingException {
		
		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		String mailContent = "<h1>ㅋㅋㅈㅅ</h1>"+
				"<p>죄송하지만 거절한다!!</p>";
	
		try {
			helper.setSubject("[SODA 관리자] 미팅룸 생성 관련 메일");
			helper.setText(mailContent, true);
			helper.setTo(email);
			mailSender.send(mail);
			
		}catch(MessagingException e) {
			e.printStackTrace();
			System.out.println(e);
			return true;
		}
		return false;
	}
}
