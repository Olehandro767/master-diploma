package ua.edu.ontu.service.student_assistant_tg_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.CallbackQueryHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.MessageHandler;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class StudentAssistantBot extends TelegramLongPollingBot {

    private final BotEntryPointPropertiesDTO entryPointProperties;
    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

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
        if (update.hasCallbackQuery()) {
            this.callbackQueryHandler.handle(this, update.getCallbackQuery());
        } else {
            this.messageHandler.handle(this, update.getMessage());
        }
    }

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
    }
}