package ua.edu.ontu.service.student_assistant_tg_bot.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotEntryPointPropertiesDTOTest {

	@Test
	void test() {
		String telegramDirectoryPath = "./src/test/resources/test_env/";
		String telegramEnvFilePath = ".env";
		var entryPointProperty = new BotEntryPointPropertiesDTO(telegramDirectoryPath, telegramEnvFilePath);
		assertEquals(entryPointProperty.getName(), "Ontu_test_bot");
		assertEquals(entryPointProperty.getToken(), "1234:fwknefw");
	}
}