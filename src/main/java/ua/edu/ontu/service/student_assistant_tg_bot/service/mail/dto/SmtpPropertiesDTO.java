package ua.edu.ontu.service.student_assistant_tg_bot.service.mail.dto;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SmtpPropertiesDTO {

	private final Properties properties;
	private final String email;
	private final String smtpPassword;

	public Session getSmtpSession() {
		return Session.getInstance(this.properties, new SmtpAuthenticator(this.email, this.smtpPassword));
	}

	@RequiredArgsConstructor
	private class SmtpAuthenticator extends Authenticator {

		private final String login;
		private final String password;

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(this.login, this.password);
		}
	}
}