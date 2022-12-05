package ua.edu.ontu.service.student_assistant_tg_bot.handler.helpful.message;

import static ua.edu.ontu.service.student_assistant_tg_bot.util.AsyncUtil.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.common.CommonHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.service.mail.MailService;
import ua.edu.ontu.service.student_assistant_tg_bot.util.LanguageUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementMessageHandler {

	private final CommonHandler commonHandler;
	private final LanguageUtil languageUtil;
	private final MailService mailService;
	private boolean supportPhotoFile = false;
	private boolean supportVideoFile = true;
	private boolean supportDocumentFile = true;

	private String[] getAdvertisementKeys() {
		return new String[] { "#ONTU_advertisement: ", };
	}

	private boolean sendToEmail(DefaultAbsSender sender, Message message, int listSize, List<java.io.File> files,
			String messageString) {
		try {
			if (files.size() >= listSize) {
				var user = message.getFrom();
				String langCode = this.languageUtil.filterLanguageCode(user.getLanguageCode());
				String firstName = user.getFirstName();
				String lastName = user.getLastName();
				String userName = user.getUserName();
				long chanId = message.getChatId();
				var emailMessage = this.mailService.configureMimeMessage(
						this.mailService.getAdvertisementEmailAddress(),
						"Оголошення від '" + firstName + " " + lastName + "' (@" + userName + ")");
				var textMimeBodyPart = new MimeBodyPart();
				var mimeMultipart = new MimeMultipart();
				String msg = "";

				for (String key : getAdvertisementKeys()) {
					if (messageString.substring(0, key.length()).equals(key)) {
						msg = messageString.substring(key.length());
						break;
					}
				}

				textMimeBodyPart.setContent(msg, "text/html; charset=utf-8");
				mimeMultipart.addBodyPart(textMimeBodyPart);

				for (java.io.File file : files) {
					var fileMimeBodyPart = new MimeBodyPart();
					fileMimeBodyPart.attachFile(file);
					mimeMultipart.addBodyPart(fileMimeBodyPart);
				}

				emailMessage.setContent(mimeMultipart);
				this.mailService.sendMail(emailMessage);
				sender.execute(commonHandler.createMessage(chanId,
						this.languageUtil.getPropertyValueByKey(langCode, "post-an-advertisement.sended_email")));
				return true;
			}
			return false;
		} catch (NullPointerException | TelegramApiException | IOException | MessagingException exception) {
			log.error(exception.getMessage(), exception);
		}
		return true;
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

	// FIX - It's Cognitive Complexity (use SonarLint)
	public void handle(DefaultAbsSender sender, Message message, String messageString) throws TelegramApiException {
		String userMessage = this.getMessage(messageString);

		if (Objects.nonNull(userMessage) && this.validateUserMessage(userMessage)) {
			var telegramFiles = new ArrayList<File>();
			var files = new ArrayList<java.io.File>();

			if (this.supportPhotoFile && message.hasPhoto()) {
				for (var file : message.getPhoto()) {
					telegramFiles.add(sender.execute(new GetFile(file.getFileId())));
				}
			}
			if (this.supportVideoFile && message.hasVideo()) {
				telegramFiles.add(sender.execute(new GetFile(message.getVideo().getFileId())));
			}
			if (this.supportDocumentFile && message.hasDocument()) {
				telegramFiles.add(sender.execute(new GetFile(message.getDocument().getFileId())));
			}

			var filePathListSize = new AtomicInteger(telegramFiles.size() - 1);

			if (!telegramFiles.isEmpty()) {
				for (File telegramFile : telegramFiles) {
					sender.downloadFileAsync(telegramFile,
							async((fileName, file) -> files.add(file), (fileName, exception) -> {
								log.error(exception.getMessage(), exception);
								filePathListSize.set(filePathListSize.get() - 1);
							}));
				}
				CompletableFuture.runAsync(() -> {
					while (!this.sendToEmail(sender, message, filePathListSize.get(), files, messageString))
						;
				});
			} else {
				this.sendToEmail(sender, message, 0, files, messageString);
			}
		}
	}
}