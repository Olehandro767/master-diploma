package ua.edu.ontu.service.student_assistant_tg_bot.handler;

import org.telegram.telegrambots.bots.DefaultAbsSender;

public interface ITelegramBotHandler<T> {

	void handle(DefaultAbsSender sender, T t) throws Exception;
}