package ua.edu.ontu.service.student_assistant_tg_bot.service.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.Activity;
import ua.edu.ontu.service.student_assistant_tg_bot.util.ActivityUtil;

@Slf4j
public class TelegramBotCompiler {

	public void compile(BotEntryPointPropertiesDTO botEntryPointPropertiesDTO, ActivityUtil activityUtil,
			TelegramBotCallbackDispatcher telegramBotCallbackDispatcher) throws FileNotFoundException {
		File activitiesDirectory = new File(botEntryPointPropertiesDTO.getBotDirectory() + "/activity");
		var activitiesFiles = activitiesDirectory.listFiles();
		var activities = new ArrayList<Activity>();

		if (Objects.nonNull(activitiesFiles)) {
			for (var file : activitiesFiles) {
				if (file.isFile()) {
					activities.add(activityUtil.convertYamlFileToActivity(new FileInputStream(file)));
				}
			}
		} else {
			TelegramBotCompiler.log.error("Directory is not exist or empty");
			System.exit(-1);
		}

		telegramBotCallbackDispatcher.configureDispatcher(activities.toArray(Activity[]::new));
	}
}