package ua.edu.ontu.service.student_assistant_tg_bot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.Activity;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContent;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ParsedActivity;
import ua.edu.ontu.service.student_assistant_tg_bot.util.ActivityUtil;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramBotCallbackDispatcher {

    private final ActivityUtil activityUtil;
    private final BotEntryPointPropertiesDTO botEntryPointPropertiesDTO;
    @Getter
    private Map<String, ActivityContentType> callbacksMap;
    @Getter
    private Map<String, ParsedActivity> activities;
    @Getter
    private ActivityContent[] messageTypeActivities;

    public ActivityContent getMessageTypeActivity(String callback) {
        for (var activity : this.messageTypeActivities) {
            if (activity.callback().equals(callback)) {
                return activity;
            }
        }

        return null;
    }

    public void configureDispatcher(Activity[] activities) {
        this.callbacksMap = new HashMap<>();
        this.activities = new HashMap<>();
        var messageTypeActivitiesList = new ArrayList<ActivityContent>();

        for (var activity : activities) {
            var filteredActivityContent = Arrays.stream(activity.content())
                    .filter(activityContent -> activityContent.type() != ActivityContentType.ACTIVITY
                            && activityContent.type() != ActivityContentType.LINK)
                    .toList();

            for (var activityContent : filteredActivityContent) {
                if (activityContent.type() == ActivityContentType.TEXT_MESSAGE) {
                    this.callbacksMap.put(activityContent.callback(), ActivityContentType.TEXT_MESSAGE);
                    messageTypeActivitiesList.add(activityContent);
                }
            }
            this.activities.put(
                    activity.activityName(),
                    new ParsedActivity(activity.activityText(), activity.content())
            );
            this.callbacksMap.put(activity.activityName(), ActivityContentType.ACTIVITY);
        }

        this.messageTypeActivities = messageTypeActivitiesList.toArray(ActivityContent[]::new);
    }

    @PostConstruct
    private void postConstruct() throws FileNotFoundException {
        new TelegramBotCompiler()
                .compile(this.botEntryPointPropertiesDTO, this.activityUtil, this);
    }
}