package ua.edu.ontu.service.student_assistant_tg_bot.handler.helpful.message;

import static ua.edu.ontu.service.student_assistant_tg_bot.util.AsyncUtil.async;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.bot.BotContextDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.ITelegramBotHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.common.CommonHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.service.mail.MailService;
import ua.edu.ontu.service.student_assistant_tg_bot.util.LanguageUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementMessageHandler implements ITelegramBotHandler<Message> {

	private final BotContextDTO botContextDTO;
	private final CommonHandler commonHandler;
	private final LanguageUtil languageUtil;
	private final MailService mailService;

	private String[] getAdvertisementKeys() {
		return new String[] { "#ONTU_advertisement: ", };
	}

	private void sendToEmail(DefaultAbsSender sender, Message message, int index, int listSize, List<File> files,
			String userMessage) {
		try {
			if (index >= listSize) {
				var user = message.getFrom();
				String langCode = this.languageUtil.filterLanguageCode(user.getLanguageCode());
				String firstName = user.getFirstName();
				String lastName = user.getLastName();
				String userName = user.getUserName();
				long chanId = message.getChatId();
//				this.mailService.configureMimeMessage(this.mailService.getAdvertisementEmailAddress(),
//						"Оголошення від '" + firstName + " " + lastName + "' (" + userName + ")");
				sender.execute(commonHandler.createMessage(chanId,
						this.languageUtil.getPropertyValueByKey(langCode, "post-an-advertisement.sended_email")));
				this.commonHandler.handle(sender, message, langCode, chanId, "/start");
			}
		} catch (NullPointerException | TelegramApiException | IOException
//				| MessagingException 
		exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	private String getMessage(String userMessage) {
		String[] keys = this.getAdvertisementKeys();

		for (String key : keys) {
			if (userMessage.substring(0, key.length()).equals(key)) {
				return userMessage.substring(key.length()).trim();
			}
		}

		return null;
	}

	private boolean validateUserMessage(String userMessage) {
		return userMessage.length() > 1;
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
		String userMessage = this.getMessage(message.getText());

		if (this.validateUserMessage(userMessage)) {
			var filePathList = new ArrayList<String>();
			var files = new ArrayList<File>();

			if (message.hasPhoto()) {
				for (var file : message.getPhoto()) {
					filePathList.add(file.getFilePath());
				}
			}
			if (message.hasVideo()) {
				filePathList.add(message.getVideo().getFileUniqueId());
			}
			if (message.hasDocument()) {
				filePathList.add(message.getDocument().getFileUniqueId());
			}

			final int filePathListSize = filePathList.size() - 1;
			final var atomicInteger = new AtomicInteger(0);

			for (String path : filePathList) {
				this.botContextDTO.getBot().downloadFileAsync(path, async(String.class, (fileName, file) -> {
					atomicInteger.set(atomicInteger.get() + 1);
					files.add(file);
					this.sendToEmail(sender, message, atomicInteger.get(), filePathListSize, files, userMessage);
				}, (fileName, exception) -> {
					atomicInteger.set(atomicInteger.get() + 1);
					log.error(exception.getMessage(), exception);
					this.sendToEmail(sender, message, atomicInteger.get(), filePathListSize, files, userMessage);
				}));
			}
		}
	}
}