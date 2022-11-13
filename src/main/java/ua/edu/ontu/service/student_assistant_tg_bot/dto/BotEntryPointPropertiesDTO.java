package ua.edu.ontu.service.student_assistant_tg_bot.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;

@Data
@Component
public class BotEntryPointPropertiesDTO {

	private final String botDirectory;
	private final String langDirectory;
	private final String name;
	private final String token;

	public BotEntryPointPropertiesDTO(@Value("${telegram-bot.directory}") String entryPointDirectory,
			@Value("${telegram-bot.env-file}") String fileName) {
		this.botDirectory = entryPointDirectory;
		this.langDirectory = entryPointDirectory + "/lang/";
		var envFile = Dotenv.configure().directory(this.botDirectory).filename(fileName).load();
		this.name = envFile.get("bot_name");
		this.token = envFile.get("bot_token");
	}
}