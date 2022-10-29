package ua.edu.ontu.service.student_assistant_tg_bot.dto.activity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActivityContentType {

    NONE("none"),
    ACTIVITY("activity"),
    LINK("link"),
    TEXT_MESSAGE("text-message");

    @Getter
    private final String lowercaseName;
}