package ua.edu.ontu.service.student_assistant_tg_bot.dto.bot;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.StudentAssistantBot;

@Data
@Component
public class BotContextDTO {

	private StudentAssistantBot bot;
}