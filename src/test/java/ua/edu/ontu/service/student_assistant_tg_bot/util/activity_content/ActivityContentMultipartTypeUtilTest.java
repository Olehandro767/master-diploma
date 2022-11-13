package ua.edu.ontu.service.student_assistant_tg_bot.util.activity_content;

import org.junit.jupiter.api.Test;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity_content.ACMultipartType;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityContentMultipartTypeUtilTest {

	@Test
	void partOfContentIsValidTest() {
		var util = new ActivityContentMultipartTypeUtil();
		assertTrue(util.partOfContentIsValid("@key smth"));
		assertFalse(util.partOfContentIsValid("@key smth @text smth"));
	}

	@Test
	void getTypeFromContentStringTest() {
		var util = new ActivityContentMultipartTypeUtil();
		assertEquals(ACMultipartType.IMAGE, util.getTypeFromContentString("@img smth"));
		assertEquals(ACMultipartType.TEXT, util.getTypeFromContentString("@text smth"));
	}

	@Test
	void getContentFromMultipartContentPartTest() {
		var util = new ActivityContentMultipartTypeUtil();
		assertEquals("smth", util.getContentFromMultipartContentPart(ACMultipartType.TEXT, "@text smth"));
	}

	@Test
	void parseMultipartContentStringTest() {
		var util = new ActivityContentMultipartTypeUtil();
		String multipartContentString = "@img https://studlifeod.ontu.edu.ua/wp-content/uploads/2019/05/Gur2.jpg:::\n"
				+ "@text gur 2";
		var result = util.parseMultipartContentString(multipartContentString);
		assertTrue(result[0].type() == ACMultipartType.IMAGE && result[0].content().contains("http"));
		assertTrue(result[1].type() == ACMultipartType.TEXT && result[1].content().equals("gur 2"));
	}
}