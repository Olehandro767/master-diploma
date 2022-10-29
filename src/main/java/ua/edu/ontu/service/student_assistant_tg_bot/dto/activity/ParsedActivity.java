package ua.edu.ontu.service.student_assistant_tg_bot.dto.activity;

public record ParsedActivity(String activityText, ActivityContent[] contents) {
}