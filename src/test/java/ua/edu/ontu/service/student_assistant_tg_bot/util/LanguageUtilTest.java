package ua.edu.ontu.service.student_assistant_tg_bot.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LanguageUtilTest {

	@Test
	void test() throws IOException {
		var mockProperties = Mockito.mock(BotEntryPointPropertiesDTO.class);
		Mockito.when(mockProperties.getBotDirectory()).thenReturn("./src/test/resources");
		Mockito.when(mockProperties.getLangDirectory()).thenReturn("./src/test/resources");
		assertEquals(mockProperties.getBotDirectory(), "./src/test/resources");
		assertEquals(mockProperties.getLangDirectory(), "./src/test/resources");
		var activityUtil = new ActivityUtil();
		var activity = activityUtil
				.convertYamlFileToActivity(new FileInputStream(mockProperties.getBotDirectory() + "/start.yaml"));
		var util = new LanguageUtil(mockProperties);
		String translatedLine = util.getTranslatedLineForActivity("ua", activity.activityName(),
				activity.content()[0].label());
		assertTrue(translatedLine.contains("Актуальні новини"));
	}
}