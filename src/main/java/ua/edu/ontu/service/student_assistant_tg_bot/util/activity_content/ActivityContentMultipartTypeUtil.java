package ua.edu.ontu.service.student_assistant_tg_bot.util.activity_content;

import org.springframework.stereotype.Service;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity_content.ACMultipartContent;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity_content.ACMultipartType;

import java.nio.charset.StandardCharsets;

@Service
public class ActivityContentMultipartTypeUtil {

	public boolean partOfContentIsValid(String content) {
		byte key = '@';
		byte[] contentByteArray = content.getBytes(StandardCharsets.UTF_8);
		int count = 0;
		boolean result = true;

		for (byte character : contentByteArray) {
			if (character == key) {
				count++;
			} else if (count > 1) {
				result = false;
				break;
			}
		}

		return result;
	}

	public ACMultipartType getTypeFromContentString(String content) {
		ACMultipartType[] types = { ACMultipartType.IMAGE, ACMultipartType.TEXT, };

		for (var type : types) {
			if (content.contains(type.getKey())) {
				return type;
			}
		}

		return null;
	}

	public String getContentFromMultipartContentPart(ACMultipartType type, String part) {
		return part.replace(type.getKey(), "").trim();
	}

	public ACMultipartContent[] parseMultipartContentString(String multipartContent) {
		String[] multipartContentParts = multipartContent.split(":::");
		var result = new ACMultipartContent[multipartContentParts.length];

		for (String part : multipartContentParts) {
			if (!this.partOfContentIsValid(part)) {
				throw new IllegalArgumentException(
						"Part of Multipart Activity Content Type can contains only one @ key: \n\t" + part);
			}
		}
		for (int i = 0; i < result.length; i++) {
			var type = this.getTypeFromContentString(multipartContentParts[i]);
			result[i] = new ACMultipartContent(type,
					this.getContentFromMultipartContentPart(type, multipartContentParts[i]));
		}

		return result;
	}
}