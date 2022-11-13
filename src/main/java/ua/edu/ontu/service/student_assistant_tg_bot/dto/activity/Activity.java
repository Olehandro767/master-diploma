package ua.edu.ontu.service.student_assistant_tg_bot.dto.activity;

public record Activity(String activityName, String activityText, ActivityContent[] content) {
}