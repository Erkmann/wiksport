package br.com.russo.wiksport.config;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	public String enviarEmail(String to, String subject, String mesage) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(mesage, true);
			mailSender.send(mail);

			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar e-mail";
		}
	}

}
