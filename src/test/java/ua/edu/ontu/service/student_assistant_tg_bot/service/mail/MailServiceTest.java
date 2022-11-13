package ua.edu.ontu.service.student_assistant_tg_bot.service.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.jupiter.api.Test;

public class MailServiceTest {

	@Test
	void sendTest() throws MessagingException { // FIXME
		var properties = new Properties() {
			{
				put("mail.smtp.auth", "true");
				put("mail.smtp.host", "smtp.gmail.com");
				put("mail.smtp.socketFactory.port", "465");
				put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			}
		};
		var session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("****", "****");
			}
		});
		var message = new MimeMessage(session) {
			{
				setFrom("*****");
				setRecipients(Message.RecipientType.TO, InternetAddress.parse("*******"));
				setSubject("Test javax.mail message");
			}
		};
		var bodyPart = new MimeBodyPart() {
			{
				setContent("Test CONTENT !!!!", "text/html; charset=utf-8");
			}
		};
		var multipart = new MimeMultipart() {
			{
				addBodyPart(bodyPart);
			}
		};
		message.setContent(multipart);
		Transport.send(message);
	}
}