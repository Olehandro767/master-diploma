package ua.edu.ontu.service.student_assistant_tg_bot.handler;

import static ua.edu.ontu.service.LogUtil.TELEGRAM_CLIENT_REQUEST_INFO;
import static ua.edu.ontu.service.LogUtil.logInfo;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.student_assistant_tg_bot.handler.common.CommonHandler;
import ua.edu.ontu.service.student_assistant_tg_bot.util.TelegramLogUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackQueryHandler implements ITelegramBotHandler<CallbackQuery> {

	private final TelegramLogUtil logUtil;
	private final CommonHandler commonHandler;

	@Override
	public void handle(DefaultAbsSender sender, CallbackQuery callbackQuery) {
		var userInfo = callbackQuery.getFrom();
		var message = callbackQuery.getMessage();
		String logInfo = "Callback query -> " + this.logUtil.getUserRequestLog(userInfo, message);
		logInfo(log, TELEGRAM_CLIENT_REQUEST_INFO, logInfo);
		this.commonHandler.handle(sender, message, userInfo.getLanguageCode(), message.getChatId(),
				callbackQuery.getData());
	}
}
