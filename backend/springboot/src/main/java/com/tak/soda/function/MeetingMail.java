package com.tak.soda.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MeetingMail {
    @Autowired
    JavaMailSender mailSender;

    public boolean sendMail(String token, String email) throws javax.mail.MessagingException {

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        String mailContent = "<h1>[인증번호]</h1>"+
                "</br>" +
                "<p>미팅 인증번호는 "+token+ " 입니다.</p>";

        try {
            helper.setSubject("[SODA 관리자] 면접방 참가 인증번호");
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
