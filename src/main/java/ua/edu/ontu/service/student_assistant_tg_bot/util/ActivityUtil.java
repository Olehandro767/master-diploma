package ua.edu.ontu.service.student_assistant_tg_bot.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.Activity;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContent;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;

@Service
public class ActivityUtil {

	public ActivityContentType getActivityContentTypeFromString(String value) {
		var lowerCaseValue = value.toLowerCase();
		ActivityContentType[] types = { ActivityContentType.ACTIVITY, ActivityContentType.LINK,
				ActivityContentType.TEXT_MESSAGE, ActivityContentType.IMAGE, ActivityContentType.QUESTION,
				ActivityContentType.MULTIPART, };

		for (ActivityContentType type : types) {
			if (type.getLowercaseName().equals(lowerCaseValue)) {
				return type;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public Activity convertYamlFileToActivity(InputStream inputStream) {
		var yaml = new Yaml();
		Map<String, Object> map = yaml.load(inputStream);
		return new Activity((String) map.get("activity-name"), (String) map.get("activity-text"),
				new ArrayList<Map<String, String>>((List<Map<String, String>>) map.get("content")).stream()
						.map(hashMap -> {
							var fullRowValue = hashMap.get("fullRow");
							return new ActivityContent(hashMap.get("callback"), hashMap.get("label"),
									this.getActivityContentTypeFromString(hashMap.get("type")), hashMap.get("content"),
									Objects.nonNull(fullRowValue) && fullRowValue.equalsIgnoreCase("true"));
						}).toList().toArray(ActivityContent[]::new));
	}
}