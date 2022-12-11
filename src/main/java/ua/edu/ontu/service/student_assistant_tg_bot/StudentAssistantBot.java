package ua.edu.ontu.service.student_assistant_tg_bot;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.bot.BotContextDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.CallbackQueryHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.MessageHandler;

@Component
@RequiredArgsConstructor
public class StudentAssistantBot extends TelegramLongPollingBot {

	private final BotContextDTO botContextDTO;
	private final MessageHandler messageHandler;
	private final CallbackQueryHandler callbackQueryHandler;
	private final BotEntryPointPropertiesDTO entryPointProperties;

	@Override
	public String getBotUsername() {
		return this.entryPointProperties.getName();
	}

	@Override
	public String getBotToken() {
		return this.entryPointProperties.getToken();
	}

	@Override
	public void onUpdateReceived(Update update) {
		var message = update.getMessage();

		if (update.hasCallbackQuery()) {
			this.callbackQueryHandler.handle(this, update.getCallbackQuery());
		} else if ((message.hasPhoto() || message.hasDocument() || message.hasVideo())
				&& (Objects.isNull(message.getCaption()) && Objects.isNull(message.getText()))) {
			// no supported
		} else {
			this.messageHandler.handle(this, message);
		}
	}

	@PostConstruct
	public void postConstruct() throws TelegramApiException {
		new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
		this.botContextDTO.setBot(this);
	}
}
