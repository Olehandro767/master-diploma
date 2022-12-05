package ua.edu.ontu.service.student_assistant_tg_bot.handler;

import static ua.edu.ontu.service.LogUtil.TELEGRAM_CLIENT_REQUEST_INFO;
import static ua.edu.ontu.service.LogUtil.logInfo;

import java.util.Objects;

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
import ua.edu.ontu.service.student_assistant_tg_bot.util.TelegramLogUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler implements ITelegramBotHandler<Message> {

	private final AdvertisementMessageHandler advertisementMessageHandler;
	private final CommonHandler commonHandler;
	private final LanguageUtil languageUtil;
	private final TelegramLogUtil logUtil;

	@Override
	public void handle(DefaultAbsSender sender, Message message) {
		try {
			var userInfo = message.getFrom();
			String messageString = (Objects.nonNull(message.getText())) ? message.getText() : message.getCaption();
			String logInfo = "Message query -> " + this.logUtil.getUserRequestLog(userInfo, message)
					+ ", user message: " + messageString;
			logInfo(log, TELEGRAM_CLIENT_REQUEST_INFO, logInfo);
			long chatId = message.getChatId();
			String langCode = userInfo.getLanguageCode();

			if (message.hasEntities()) {
				for (var messageEntity : message.getEntities()) {
					if (messageEntity.getType().equalsIgnoreCase("bot_command")) {
						this.commonHandler.handle(sender, message, langCode, chatId, messageEntity.getText());
					} else if (messageEntity.getType().equalsIgnoreCase("hashtag")
							&& this.advertisementMessageHandler.checkUserTextOnStartKeywords(messageString)) {
						this.advertisementMessageHandler.handle(sender, message, messageString);
					}
				}
			} else if (this.advertisementMessageHandler.checkUserTextOnStartKeywords(messageString)) {
				this.advertisementMessageHandler.handle(sender, message, messageString);
			} else {
				String errorMessage = this.languageUtil.getPropertyValueByKey(langCode,
						"error.can-not-resolve-message");
				var sendMessage = new SendMessage();
				sendMessage.setChatId(chatId);
				sendMessage.setText(errorMessage);
				sender.execute(sendMessage);
			}
		} catch (TelegramApiException exception) {
			MessageHandler.log.error(exception.getMessage(), exception);
		}
	}
}