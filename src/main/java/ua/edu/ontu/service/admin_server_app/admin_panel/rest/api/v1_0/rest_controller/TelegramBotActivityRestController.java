package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.rest_controller;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.Application;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotNewActivityRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotUpdateActivityReques;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.response.telegram_bot.TelegramBotAllActivitiesResponse;
import ua.edu.ontu.service.admin_server_app.service.telegram_bot.TelegramBotActivityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/telegram-bot/activity")
public class TelegramBotActivityRestController {

	private final TelegramBotActivityService telegramBotActivityService;

	@GetMapping("/get-example-content")
	public String getExampleContent() throws IOException {
		var resource = Application.class.getClassLoader().getResourceAsStream("static/admin/example-yaml-file.yaml");
		return new String(resource.readAllBytes());
	}

	@PutMapping("/create")
	public ResponseEntity<?> create(@RequestBody TelegramBotNewActivityRequest json) {
		if (this.telegramBotActivityService.validateActivityContent(json.getActivityContent())) {
			if (!json.getActivityName().isEmpty()) {
				if (this.telegramBotActivityService.createNewActivity(json)) {
					return ResponseEntity.ok().build();
				}

				return new ResponseEntity<>("Can't create activity", HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<>("Error with activity name", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Error with activity content", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/get-all")
	public TelegramBotAllActivitiesResponse getAll() {
		return this.telegramBotActivityService.getAllActivities();
	}

	@GetMapping("/get-content")
	public ResponseEntity<String> getContent(@RequestParam String activityName) {
		String activityContent = this.telegramBotActivityService.getActivityContent(activityName);

		if (Objects.nonNull(activityContent)) {
			return ResponseEntity.ok(activityContent);
		}

		return new ResponseEntity<>("File with name" + activityName + " is not exist", HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody TelegramBotUpdateActivityReques json) {
		if (this.telegramBotActivityService.validateActivityContent(json.getNewContent())) {
			this.telegramBotActivityService.updateActivityContent(json);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String activityName) {
		if (!activityName.isEmpty()) {
			this.telegramBotActivityService.deleteActivity(activityName);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}
}