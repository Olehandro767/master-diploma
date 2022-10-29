package ua.edu.ontu.service.student_assistant_tg_bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.common.CommonHandler;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandler implements ITelegramBotHandler<CallbackQuery> {

    private final CommonHandler commonHandler;

    @Override
    public void handle(DefaultAbsSender sender, CallbackQuery callbackQuery) {
        this.commonHandler.handle(
                sender,
                callbackQuery.getMessage(),
                callbackQuery.getFrom().getLanguageCode(),
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getData()
        );
    }
}
