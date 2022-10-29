package ua.edu.ontu.service.student_assistant_tg_bot.handler.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.ontu.service.LogUtil;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;
import ua.edu.ontu.service.student_assistant_tg_bot.service.TelegramBotCallbackDispatcher;
import ua.edu.ontu.service.student_assistant_tg_bot.util.LanguageUtil;
import ua.edu.ontu.service.student_assistant_tg_bot.util.TelegramUIUtil;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonHandler {

    private final TelegramBotCallbackDispatcher dispatcher;
    private final TelegramUIUtil telegramUIUtil;
    private final LanguageUtil languageUtil;
    private final LogUtil logUtil;

    private void deleteOldMessage(DefaultAbsSender sender, long chatId, Message message) throws TelegramApiException {
        sender.execute(new DeleteMessage() {{
            setChatId(chatId);
            setMessageId(message.getMessageId());
        }});
    }

    public void handle(DefaultAbsSender sender, Message message, String langCode, long chatId, String messageString) {
        try {
            var type = this.dispatcher.getCallbacksMap().get(messageString);
            var tgUiUtil = this.telegramUIUtil;
            var langUtil = this.languageUtil;

            if (Objects.isNull(type)) {
                type = ActivityContentType.NONE;
                this.logUtil.logError(CommonHandler.log, LogUtil.TELEGRAM_CLIENT_REQUEST_ERROR,
                        "wrong callback (" + messageString + ')');
            }

            switch (type) {
                case ACTIVITY -> {
                    var activity = this.dispatcher.getActivities().get(messageString);
                    this.deleteOldMessage(sender, chatId, message);
                    sender.execute(new SendMessage() {{
                        setChatId(chatId);
                        setParseMode("HTML");

                        if (Objects.nonNull(activity.activityText())) {
                            setText(langUtil.getTranslatedLineForActivity(langCode, messageString, activity.activityText()));
                        } else {
                            setText(langUtil.getPropertyValueByKey(langCode, "default.activity-message"));
                        }

                        setReplyMarkup(tgUiUtil.buildUI(messageString, activity.contents(), langCode));
                    }});
                }
                case TEXT_MESSAGE -> {
                    String responseMessage = this.dispatcher.getMessageTypeActivity(messageString).content();
                    sender.execute(new SendMessage() {{
                        setChatId(chatId);
                        setParseMode("HTML");
                        setText(langUtil.getTranslatedLineForActivity(langCode, messageString, responseMessage));
                    }});
                }
                default -> {
                    var activity = this.dispatcher.getActivities().get("/start");
                    String errorMessage = this.languageUtil
                            .getPropertyValueByKey(langCode, "error.can-not-resolve-message");
                    sender.execute(new SendMessage() {{
                        setChatId(chatId);
                        setParseMode("HTML");
                        setText(errorMessage);
                        setReplyMarkup(tgUiUtil.buildUI("/start", activity.contents(), langCode));
                    }});
                }
            }
        } catch (TelegramApiException | IOException | NullPointerException exception) {
            CommonHandler.log.error(exception.getMessage(), exception);
        }
    }
}