package ua.edu.ontu.service.student_assistant_tg_bot.dto.activity;

public record ActivityContent(String callback, String label, ActivityContentType type, String content,
		boolean fullRow) {
}