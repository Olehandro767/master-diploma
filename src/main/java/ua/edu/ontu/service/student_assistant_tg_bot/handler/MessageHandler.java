package ua.edu.ontu.service.student_assistant_tg_bot.handler;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.common.CommonHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.helpful.message.AdvertisementMessageHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.util.LanguageUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler implements ITelegramBotHandler<Message> {

	private final AdvertisementMessageHandler advertisementMessageHandler;
	private final LanguageUtil languageUtil;
	private final CommonHandler commonHandler;

	@Override
	public void handle(DefaultAbsSender sender, Message message) {
		try {
			long chatId = message.getChatId();
			String langCode = message.getFrom().getLanguageCode();

			if (message.hasEntities()) {
				for (var messageEntity : message.getEntities()) {
					if (messageEntity.getType().equalsIgnoreCase("bot_command")) {
						this.commonHandler.handle(sender, message, langCode, chatId, messageEntity.getText());
					} else if (messageEntity.getType().equalsIgnoreCase("hashtag")) {
						if (this.advertisementMessageHandler.checkUserTextOnStartKeywords(message.getText())) {
							this.advertisementMessageHandler.handle(sender, message);
						}
					}
				}
			} else {
				String errorMessage = this.languageUtil.getPropertyValueByKey(langCode,
						"error.can-not-resolve-message");
				sender.execute(new SendMessage() {
					{
						setChatId(chatId);
						setText(errorMessage);
					}
				});
			}
		} catch (TelegramApiException | IOException exception) {
			MessageHandler.log.error(exception.getMessage(), exception);
		}
	}
}