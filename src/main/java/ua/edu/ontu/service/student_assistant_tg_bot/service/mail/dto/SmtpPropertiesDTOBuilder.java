package ua.edu.ontu.service.student_assistant_tg_bot.service.mail.dto;

import java.util.Objects;
import java.util.Properties;

import lombok.Builder;
import lombok.Getter;

/**
 * SMTP - Simple Mail Transfer Protocol
 */
@Builder
public class SmtpPropertiesDTOBuilder {

	@Getter
	private Properties customSmtpProperties;
	@Getter
	private String smtpHost;
	@Getter
	private String smtpPort;
	@Getter
	private String sslPort;
	@Getter
	private String tlsPort;

	@SuppressWarnings("serial")
	public Properties getProperties() {
		if (Objects.nonNull(this.customSmtpProperties)) {
			return this.customSmtpProperties;
		}
		return new Properties() {
			{
				put("mail.smtp.auth", "true");
				put("mail.smtp.host", smtpHost);
				put("mail.smtp.socketFactory.port", smtpPort);
				put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			}
		};
	}
}