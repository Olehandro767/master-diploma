package ua.edu.ontu.service.student_assistant_tg_bot.service.mail.dto;

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
	
	public Properties getProperties() {
		return null; // TODO
	}
}