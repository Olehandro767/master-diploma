package ua.edu.ontu.service.student_assistant_tg_bot.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;

@Service
@RequiredArgsConstructor
public class LanguageUtil {

	private final BotEntryPointPropertiesDTO botProperties;

	public String getPropertyValueByKey(String langCode, String propertyKey) throws IOException {
		var property = new Properties();
		property.load(new InputStreamReader(new FileInputStream(
				this.botProperties.getLangDirectory() + '/' + this.filterLanguageCode(langCode) + ".properties"),
				StandardCharsets.UTF_8));
		return property.getProperty(propertyKey);
	}

	public String filterLanguageCode(String langCode) {
		return switch (langCode) {
		case "ru", "ua" -> "ua";
//            default -> "en";
		default -> "ua";
		};
	}

	public String getTranslatedLineForActivity(String langCode, String activityName, String stringContent)
			throws IOException {
		String startKeyPointer = "${{";
		String endKeyPointer = "}}";
		var property = new Properties();
		property.load(new InputStreamReader(new FileInputStream(
				this.botProperties.getLangDirectory() + '/' + this.filterLanguageCode(langCode) + ".properties"),
				StandardCharsets.UTF_8));

		while (stringContent.contains(startKeyPointer)) {
			String key = stringContent.substring(stringContent.indexOf(startKeyPointer) + startKeyPointer.length(),
					stringContent.indexOf(endKeyPointer));
			String propertyKey = (key.contains("nav_button")) ? key : activityName + '.' + key;
			String propertyLine = property.getProperty(propertyKey);
			stringContent = stringContent.replace(startKeyPointer + key + endKeyPointer, propertyLine);
		}

		return stringContent;
	}
}