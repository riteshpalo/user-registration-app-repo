package com.rpcl.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * This class is used to perform the email functionality
 * @author Ritesh
 *
 */
@Component
public class EmailUtils {
	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * This method is used to send mail by passing to,subject,body
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 */
	public void sendMailWithHiperlink(String to,String subject,String body) throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(msg);
	}
}
