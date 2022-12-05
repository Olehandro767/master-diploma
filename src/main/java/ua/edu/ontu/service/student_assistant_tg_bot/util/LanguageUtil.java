package ua.edu.ontu.service.student_assistant_tg_bot.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class LanguageUtil {

	private final BotEntryPointPropertiesDTO botProperties;

	public String getPropertyValueByKey(String langCode, String propertyKey) {
		InputStreamReader inputStreamReader = null;
		try {
			String path = this.botProperties.getLangDirectory() + '/' + this.filterLanguageCode(langCode)
					+ ".properties";
			var property = new Properties();
			var fileInputStream = new FileInputStream(path);
			inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
			property.load(inputStreamReader);
			inputStreamReader.close();
			return property.getProperty(propertyKey);
		} catch (IOException exception) {
			throw new IllegalArgumentException();
		}
	}

	public String filterLanguageCode(String langCode) {
		return switch (langCode) {
		case "ru", "ua" -> "ua";
//            default -> "en";
		default -> "ua";
		};
	}

	public String getTranslatedLineForActivity(String langCode, String activityName, String stringContent) {
		String path = this.botProperties.getLangDirectory() + '/' + this.filterLanguageCode(langCode) + ".properties";
		String startKeyPointer = "${{";
		String endKeyPointer = "}}";
		var property = new Properties();
		try {
			var fileInputStream = new FileInputStream(path);
			var inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
			property.load(inputStreamReader);
			inputStreamReader.close();
		} catch (IOException exception) {
			throw new IllegalArgumentException();
		}
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