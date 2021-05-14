package com.tak.soda.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class ApproveMail {
    @Autowired
    JavaMailSender mailSender;

    public boolean sendMail(String authCode, String inviteCode, String email) throws javax.mail.MessagingException {

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        String mailContent = "<h1>[기업 인증번호]</h1>"+
                "<p>기업 인증번호는 "+authCode+ " 입니다.</p>" +
                "<h1>[미팅 인증번호]</h1>"+
                "<p>미팅 인증번호는 "+inviteCode+ " 입니다.</p>"
                ;

        try {
            helper.setSubject("[SODA 관리자] 미팅룸 생성 인증번호");
            helper.setText(mailContent, true);
            helper.setTo(email);
            mailSender.send(mail);

        }catch(MessagingException e) {
            // e.printStackTrace();
            // System.out.println(e);
            return true;
        }
        return false;
    }
}
