package ua.edu.ontu.service.student_assistant_tg_bot.dto.activity_content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ACMultipartType {
	IMAGE("@img"), TEXT("@text");

	@Getter
	private final String key;
}