package ua.edu.ontu.service.student_assistant_tg_bot.handler.common;

import static ua.edu.ontu.service.LogUtil.TELEGRAM_CLIENT_REQUEST_ERROR;
import static ua.edu.ontu.service.LogUtil.logError;

import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity_content.ACMultipartType;
import ua.edu.ontu.service.student_assistant_tg_bot.service.bot.TelegramBotCallbackDispatcher;
import ua.edu.ontu.service.student_assistant_tg_bot.util.LanguageUtil;
import ua.edu.ontu.service.student_assistant_tg_bot.util.TelegramUIUtil;
import ua.edu.ontu.service.student_assistant_tg_bot.util.activity_content.ActivityContentMultipartTypeUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonHandler {

	private final ActivityContentMultipartTypeUtil multipartTypeUtil;
	private final TelegramBotCallbackDispatcher dispatcher;
	private final TelegramUIUtil telegramUIUtil;
	private final LanguageUtil languageUtil;

	private void deleteOldMessage(DefaultAbsSender sender, long chatId, Message message) throws TelegramApiException {
		var deleteMessage = new DeleteMessage();
		deleteMessage.setChatId(chatId);
		deleteMessage.setMessageId(message.getMessageId());
		sender.execute(deleteMessage);
	}

	private void badRequest(DefaultAbsSender sender, String langCode, long chatId, Exception exceptionArg) {
		try {
			var activity = this.dispatcher.getActivities().get("/start");
			String propertyKey;

			if (Objects.nonNull(exceptionArg)
					&& exceptionArg.getMessage().contains("message can't be deleted for everyone")) {
				propertyKey = "error.apply_updates";
			} else {
				propertyKey = "error.can-not-resolve-message";
			}

			String errorMessage = this.languageUtil.getPropertyValueByKey(langCode, propertyKey);
			var sendMessage = new SendMessage();
			sendMessage.setChatId(chatId);
			sendMessage.setParseMode("HTML");
			sendMessage.setText(errorMessage);
			sendMessage.setReplyMarkup(this.telegramUIUtil.buildUI("/start", activity.contents(), langCode));
			sender.execute(sendMessage);
		} catch (Exception exception) {
			CommonHandler.log.error(exception.getMessage(), exception);
		}
	}

	public SendMessage createMessage(long chatId, String message) {
		var sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setParseMode("HTML");
		sendMessage.setText(message);
		return sendMessage;
	}

	public void handle(DefaultAbsSender sender, Message message, String langCode, long chatId, String messageString) {
		try {
			var type = this.dispatcher.getCallbacksMap().get(messageString);

			if (Objects.isNull(type)) {
				type = ActivityContentType.NONE;
				logError(CommonHandler.log, TELEGRAM_CLIENT_REQUEST_ERROR, "wrong callback (" + messageString + ')');
			}

			switch (type) {
			case ACTIVITY -> {
				var activity = this.dispatcher.getActivities().get(messageString);
				this.deleteOldMessage(sender, chatId, message);
				var sendMessage = new SendMessage();
				sendMessage.setChatId(chatId);
				sendMessage.setParseMode("HTML");

				if (Objects.nonNull(activity.activityText())) {
					sendMessage.setText(this.languageUtil.getTranslatedLineForActivity(langCode, messageString,
							activity.activityText()));
				} else {
					sendMessage.setText(this.languageUtil.getPropertyValueByKey(langCode, "default.activity-message"));
				}

				sendMessage.setReplyMarkup(this.telegramUIUtil.buildUI(messageString, activity.contents(), langCode));
				sender.execute(sendMessage);
			}
			case TEXT_MESSAGE -> {
				String responseMessage = this.dispatcher.getMessageTypeActivity(messageString).content();
				sender.execute(this.createMessage(chatId,
						this.languageUtil.getTranslatedLineForActivity(langCode, messageString, responseMessage)));
			}
			case MULTIPART -> {
				String multipartContent = this.dispatcher.getMultipartTypeActivity(messageString).content();
				var contentArray = this.multipartTypeUtil.parseMultipartContentString(multipartContent);

				for (var content : contentArray) {
					if (content.type() == ACMultipartType.TEXT) {
						sender.execute(this.createMessage(chatId, this.languageUtil
								.getTranslatedLineForActivity(langCode, messageString, content.content())));
					} else if (content.type() == ACMultipartType.IMAGE) {
						var sendPhoto = new SendPhoto();
						sendPhoto.setPhoto(new InputFile(content.content()));
						sendPhoto.setChatId(chatId);
						sender.execute(sendPhoto);
					}
				}
			}
			default -> this.badRequest(sender, langCode, chatId, null);
			}
		} catch (TelegramApiException | IOException | NullPointerException exception) {
			CommonHandler.log.error(exception.getMessage(), exception);
			this.badRequest(sender, langCode, chatId, exception);
		}
	}
}