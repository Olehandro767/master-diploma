package ua.edu.ontu.service.student_assistant_tg_bot.util;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class TelegramLogUtil {

	public String getUserRequestLog(User user, Message message) {
		return "user name: " + user.getUserName() + ", chat id: " + message.getChatId();
	}
}