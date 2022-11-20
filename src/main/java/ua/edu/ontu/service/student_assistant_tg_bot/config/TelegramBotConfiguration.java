package ua.edu.ontu.service.student_assistant_tg_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.edu.ontu.service.student_assistant_tg_bot.service.mail.dto.SmtpPropertiesDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.service.mail.dto.SmtpPropertiesDTOBuilder;

@Configuration
public class TelegramBotConfiguration {

	@Bean
	public SmtpPropertiesDTO buildSmtpPropertiesDTO(@Value("${mail.host}") String host,
			@Value("${mail.port}") String port, @Value("${mail.application-email}") String email,
			@Value("${mail.application-email-password}") String password) {
		var builder = SmtpPropertiesDTOBuilder.builder().smtpHost(host).smtpPort(port);
		return new SmtpPropertiesDTO(builder.build().getProperties(), email, password);
	}
}