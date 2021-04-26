package com.tak.soda.function;

import java.io.InputStream;
import java.util.Random;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class RandomAccessToken {
	@Autowired
	private JavaMailSender mailSender;
//	토큰발급
	public String makeToken(int size) {
		Random random = new Random();
		
		StringBuffer buffer = new StringBuffer();
		while(buffer.length()<size) {
			int num = random.nextInt(3);
//			숫자
			if(num==0) {
				num = random.nextInt(10);
				buffer.append(num);
			}
//			소문자
			else if(num==1) {
				num = random.nextInt(10)+65;
				char cnt = (char) num;
				buffer.append(cnt);
			}
//			대문자
			else {
				num = random.nextInt(10)+97;
				char cnt = (char) num;
				buffer.append(cnt);
			}
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	public boolean sendMail(String token,String email) throws javax.mail.MessagingException {
		
		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		String mailContent = "<h1>[인증번호]</h1>"+
				"<p>인증번호는 "+token+ " 입니다.</p>";
	
		try {
			helper.setSubject("관리자 미팅룸 생성 인증번호");
			helper.setText(mailContent, true);
			helper.setTo(email);
			mailSender.send(mail);
			
		}catch(MessagingException e) {
			System.out.println("dfsdfsdfsd");
			e.printStackTrace();
			System.out.println(e);
			return true;
		}
		return false;
	}
}
