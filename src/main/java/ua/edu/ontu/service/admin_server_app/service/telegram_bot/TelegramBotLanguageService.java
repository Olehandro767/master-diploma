package ua.edu.ontu.service.admin_server_app.service.telegram_bot;

import static java.lang.String.format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotNewLanguagePackRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotUpdateLanguagePackRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.response.telegram_bot.TelegramBotAllLanguagePacksResponse;
import ua.edu.ontu.service.admin_server_app.dto.AppProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotLanguageService {

	private final AppProperties appProperties;

	public boolean validateLanguagePackContent(String content) {
		return !content.isEmpty();
	}

	@SuppressWarnings("resource")
	public boolean create(TelegramBotNewLanguagePackRequest json) {
		try {
			File file = new File(
					format("%s/lang/%s.properties", this.appProperties.telegramDirectory(), json.getLanguageCode()));

			if (file.exists()) {
				return false;
			}

			file.createNewFile();
			new FileOutputStream(file).write(json.getContent().getBytes(StandardCharsets.UTF_8));
			return true;
		} catch (IOException exception) {
			log.error(exception.getMessage(), exception);
			return false;
		}
	}

	public TelegramBotAllLanguagePacksResponse getAllLanguagePacks() {
		File[] languagePacks = new File(format("%s/lang", this.appProperties.telegramDirectory())).listFiles();
		String[] fileNames = Arrays.stream(languagePacks)
				.map(file -> file.getName().substring(0, file.getName().length() - 11)).toArray(String[]::new);
		return new TelegramBotAllLanguagePacksResponse(fileNames);
	}

	public String getContent(String langCode) {
		try {
			return new String(Files.readAllBytes(
					Path.of(format("%s/lang/%s.properties", this.appProperties.telegramDirectory(), langCode))));
		} catch (IOException exception) {
			log.error(exception.getMessage(), exception);
			return null;
		}
	}

	public void update(TelegramBotUpdateLanguagePackRequest json) {
		try {
			Files.writeString(Path.of(
					format("%s/lang/%s.properties", this.appProperties.telegramDirectory(), json.getLanguageCode())),
					json.getNewContent());
		} catch (IOException exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	public void delete(String languagePackName) {
		File languagePack = new File(
				format("%s/lang/%s.properties", this.appProperties.telegramDirectory(), languagePackName));

		if (languagePack.exists()) {
			languagePack.delete();
		}
	}
}