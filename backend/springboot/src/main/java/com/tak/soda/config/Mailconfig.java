package com.tak.soda.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class Mailconfig{
	@Bean(name = "mailSender")
	public JavaMailSender getJavaMailSender() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.transprot.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.starttls.required", true);
		properties.put("mail.debug", true);
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("ehdrjf337@gmail.com");
		mailSender.setPassword("tntjd0206@");
		mailSender.setDefaultEncoding("utf-8");
		mailSender.setJavaMailProperties(properties);
		
		return mailSender;

	}
}