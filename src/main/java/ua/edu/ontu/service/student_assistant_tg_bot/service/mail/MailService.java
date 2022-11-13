package ua.edu.ontu.service.student_assistant_tg_bot.service.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.service.mail.dto.SmtpPropertiesDTO;

//@Service
@RequiredArgsConstructor
public class MailService {

	private final SmtpPropertiesDTO smtpPropertiesDTO;

	public MimeMessage configureMimeMessage(String recipienEmailAddress, String subjectName) throws MessagingException {
		return new MimeMessage(this.smtpPropertiesDTO.getSmtpSession()) {
			{
				setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipienEmailAddress));
				setSubject(subjectName);
			}
		};
	}

	public void sendMail(Message message) throws MessagingException {
		Transport.send(message);
	}
}