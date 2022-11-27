package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot;

import lombok.Data;

@Data
public class TelegramBotUpdateActivityReques {

	private String activityName;
	private String newContent;
}