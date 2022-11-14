package ua.edu.ontu.service.student_assistant_tg_bot.service.bot;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.BotEntryPointPropertiesDTO;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.Activity;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContent;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ActivityContentType;
import ua.edu.ontu.service.student_assistant_tg_bot.dto.activity.ParsedActivity;
import ua.edu.ontu.service.student_assistant_tg_bot.util.ActivityUtil;

@Service
@RequiredArgsConstructor
public class TelegramBotCallbackDispatcher {

	private final ActivityUtil activityUtil;
	private final BotEntryPointPropertiesDTO botEntryPointPropertiesDTO;
	private ActivityContent[] messageTypeActivities;
	private ActivityContent[] questionTypeActivities;
	private ActivityContent[] multipartTypeActivities;
	@Getter
	private Map<String, ActivityContentType> callbacksMap;
	@Getter
	private Map<String, ParsedActivity> activities;

	private ActivityContent getActivityContentByCallback(String callback, ActivityContent[] activityContents) {
		for (var activity : activityContents) {
			if (activity.callback().equals(callback)) {
				return activity;
			}
		}

		return null;
	}

	public ActivityContent getMessageTypeActivity(String callback) {
		return this.getActivityContentByCallback(callback, this.messageTypeActivities);
	}

	public ActivityContent getQuestionTypeActivity(String callback) {
		return this.getActivityContentByCallback(callback, this.questionTypeActivities);
	}

	public ActivityContent getMultipartTypeActivity(String callback) {
		return this.getActivityContentByCallback(callback, this.multipartTypeActivities);
	}

	public void configureDispatcher(Activity[] activities) {
		this.callbacksMap = new HashMap<>();
		this.activities = new HashMap<>();
		var messageTypeActivitiesList = new ArrayList<ActivityContent>();
		var questionTypeActivitiesList = new ArrayList<ActivityContent>();
		var multipartTypeActivitiesList = new ArrayList<ActivityContent>();

		for (var activity : activities) {
			var filteredActivityContent = Arrays.stream(activity.content())
					.filter(activityContent -> activityContent.type() != ActivityContentType.ACTIVITY
							&& activityContent.type() != ActivityContentType.LINK)
					.toList();

			for (var activityContent : filteredActivityContent) {
				if (activityContent.type() == ActivityContentType.TEXT_MESSAGE) {
					this.callbacksMap.put(activityContent.callback(), ActivityContentType.TEXT_MESSAGE);
					messageTypeActivitiesList.add(activityContent);
				} else if (activityContent.type() == ActivityContentType.QUESTION) {
					this.callbacksMap.put(activityContent.callback(), ActivityContentType.QUESTION);
					questionTypeActivitiesList.add(activityContent);
				} else if (activityContent.type() == ActivityContentType.MULTIPART) {
					this.callbacksMap.put(activityContent.callback(), ActivityContentType.MULTIPART);
					multipartTypeActivitiesList.add(activityContent);
				}
			}
			this.activities.put(activity.activityName(),
					new ParsedActivity(activity.activityText(), activity.content()));
			this.callbacksMap.put(activity.activityName(), ActivityContentType.ACTIVITY);
		}

		IntFunction<ActivityContent[]> commonLambda = ActivityContent[]::new;
		this.messageTypeActivities = messageTypeActivitiesList.toArray(commonLambda);
		this.questionTypeActivities = questionTypeActivitiesList.toArray(commonLambda);
		this.multipartTypeActivities = multipartTypeActivitiesList.toArray(commonLambda);
	}

	@PostConstruct
	private void postConstruct() throws FileNotFoundException {
		new TelegramBotCompiler().compile(this.botEntryPointPropertiesDTO, this.activityUtil, this);
	}
}