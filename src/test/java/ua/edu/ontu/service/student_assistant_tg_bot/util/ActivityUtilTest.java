package ua.edu.ontu.service.student_assistant_tg_bot.util;

import org.junit.jupiter.api.Test;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityUtilTest {

	@Test
	void test() throws FileNotFoundException {
		var activityUtil = new ActivityUtil();
		var activity = activityUtil.convertYamlFileToActivity(new FileInputStream("./src/test/resources/start.yaml"));
		var studyingProcess = Arrays.stream(activity.content())
				.filter(activityContent -> activityContent.type() == ActivityContentType.ACTIVITY)
				.filter(activityContent -> activityContent.label().contains("studying_process")).toList().get(0);
		assertEquals(activity.activityName(), "start");
		assertTrue(Objects.nonNull(studyingProcess));
		assertEquals(studyingProcess.callback(), "studying-process");
	}
}