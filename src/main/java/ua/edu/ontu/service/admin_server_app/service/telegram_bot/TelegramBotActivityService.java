package ua.edu.ontu.service.admin_server_app.service.telegram_bot;

import static java.lang.String.format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotNewActivityRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotUpdateActivityReques;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.response.telegram_bot.TelegramBotAllActivitiesResponse;
import ua.edu.ontu.service.admin_server_app.dto.AppProperties;
import ua.edu.ontu.service.student_assistant_tg_bot.service.bot.TelegramBotCallbackDispatcher;
import ua.edu.ontu.service.student_assistant_tg_bot.service.bot.TelegramBotCompiler;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotActivityService {

	private final AppProperties appProperties;
	private final TelegramBotCallbackDispatcher telegramBotCallbackDispatcher;

	private void updateBot() {
		try {
			new TelegramBotCompiler().compile(this.telegramBotCallbackDispatcher.getBotEntryPointPropertiesDTO(),
					this.telegramBotCallbackDispatcher.getActivityUtil(), this.telegramBotCallbackDispatcher);
		} catch (FileNotFoundException exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	public boolean validateActivityContent(String content) {
		return !content.isEmpty();
	}

	@SuppressWarnings("resource")
	public boolean createNewActivity(TelegramBotNewActivityRequest json) {
		try {
			File file = new File(
					format("%s/activity/%s.yaml", this.appProperties.telegramDirectory(), json.getActivityName()));

			if (file.exists()) {
				return false;
			}
			if (!file.createNewFile()) {
				log.error("Can't create file");
			}

			new FileOutputStream(file).write(json.getActivityContent().getBytes(StandardCharsets.UTF_8));
			this.updateBot();
			return true;
		} catch (IOException exception) {
			log.error(exception.getMessage(), exception);
			return false;
		}
	}

	public TelegramBotAllActivitiesResponse getAllActivities() {
		File[] activities = new File(format("%s/activity", this.appProperties.telegramDirectory())).listFiles();
		String[] fileNames = Arrays.stream(activities)
				.map(file -> file.getName().substring(0, file.getName().length() - 5)).toArray(String[]::new);
		return new TelegramBotAllActivitiesResponse(fileNames);
	}

	public String getActivityContent(String activityName) {
		try {
			return new String(Files.readAllBytes(
					Path.of(format("%s/activity/%s.yaml", this.appProperties.telegramDirectory(), activityName))));
		} catch (IOException exception) {
			log.error(exception.getMessage(), exception);
			return null;
		}
	}

	public void updateActivityContent(TelegramBotUpdateActivityReques json) {
		try {
			Files.writeString(Path
					.of(format("%s/activity/%s.yaml", this.appProperties.telegramDirectory(), json.getActivityName())),
					json.getNewContent());
			this.updateBot();
		} catch (IOException exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	public void deleteActivity(String activityName) {
		File activity = new File(format("%s/activity/%s.yaml", this.appProperties.telegramDirectory(), activityName));

		if (activity.exists()) {
			activity.delete();
			this.updateBot();
		}
	}
}