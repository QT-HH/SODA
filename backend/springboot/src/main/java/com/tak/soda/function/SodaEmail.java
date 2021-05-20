package com.tak.soda.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class SodaEmail {
	@Autowired private JavaMailSender emailSender;
	@Autowired private SpringTemplateEngine templateEngine;
	
	public void sendMail(Mail mail) throws IOException, MessagingException {
		
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		// helper.addAttachment("logo.png", new ClassPathResource("email-logo.png"));

		String html = getHtmlContent(mail);

		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());

		emailSender.send(message);
	}

	private String getHtmlContent(Mail mail) {
		Context context = new Context();
		context.setVariables(mail.getHtmlTemplate().getProps());
		return templateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
	}
}
