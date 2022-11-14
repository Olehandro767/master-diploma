package ua.edu.ontu.service.student_assistant_tg_bot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContent;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;

@Service
@RequiredArgsConstructor
public class TelegramUIUtil {

	private final LanguageUtil languageUtil;

	public InlineKeyboardButton createButton(String activityName, ActivityContent activityContent, String langCode)
			throws IOException {
		var button = new InlineKeyboardButton(
				this.languageUtil.getTranslatedLineForActivity(langCode, activityName, activityContent.label()));

		if (activityContent.type() == ActivityContentType.LINK) {
			button.setUrl(activityContent.content());
		} else if (activityContent.type() == ActivityContentType.ACTIVITY
				|| activityContent.type() == ActivityContentType.TEXT_MESSAGE
				|| activityContent.type() == ActivityContentType.QUESTION
				|| activityContent.type() == ActivityContentType.MULTIPART) {
			button.setCallbackData(activityContent.callback());
		}

		return button;
	}

	public InlineKeyboardMarkup buildDefaultUI(String activityName, ActivityContent[] activityContents, String langCode)
			throws IOException {
		var lines = new ArrayList<List<InlineKeyboardButton>>();

		for (int i = 0; i < activityContents.length; i++) {
			var row = new ArrayList<InlineKeyboardButton>();

			for (int j = 0; j < 2 && i + j < activityContents.length; j++) {
				int localIndex = i + j;

				if (!activityContents[localIndex].fullRow()) {
					row.add(this.createButton(activityName, activityContents[localIndex], langCode));
				} else {
					row.add(this.createButton(activityName, activityContents[localIndex], langCode));
					break;
				}
			}

			i += row.size() - 1;
			lines.add(row);
		}

		return new InlineKeyboardMarkup(lines);
	}

	public InlineKeyboardMarkup buildUI(String activityName, ActivityContent[] activityContents, String langCode)
			throws IOException { // For creating elastic style builder in future
		return this.buildDefaultUI(activityName, activityContents, langCode);
	}
}