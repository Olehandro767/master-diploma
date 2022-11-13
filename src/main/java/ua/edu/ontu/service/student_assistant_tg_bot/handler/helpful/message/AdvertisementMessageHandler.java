package ua.edu.ontu.service.student_assistant_tg_bot.handler.helpful.message;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.bot.BotContextDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.ITelegramBotHandler;

@Service
@RequiredArgsConstructor
public class AdvertisementMessageHandler implements ITelegramBotHandler<Message> {

	private final BotContextDTO botContextDTO;

	private String[] getAdvertisementKeys() {
		return new String[] { "#ONTU_advertisement ", };
	}

	public boolean checkUserTextOnStartKeywords(String userText) {
		if (Objects.nonNull(userText)) {
			String[] keys = this.getAdvertisementKeys();

			for (String key : keys) {
				if (userText.substring(0, key.length()).equals(key)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void handle(DefaultAbsSender sender, Message message) throws TelegramApiException {
		for (var file : message.getPhoto()) {

		}
	}
}