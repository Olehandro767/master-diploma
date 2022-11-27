package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.rest_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotNewLanguagePackRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.telegram_bot.TelegramBotUpdateLanguagePackRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.response.telegram_bot.TelegramBotAllLanguagePacksResponse;
import ua.edu.ontu.service.admin_server_app.service.telegram_bot.TelegramBotLanguageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/telegram-bot/language")
public class TelegramBotLanguagePackRestController {

	private final TelegramBotLanguageService telegramBotLanguageService;

	@PutMapping("/create")
	public ResponseEntity<?> create(@RequestBody TelegramBotNewLanguagePackRequest json) {
		if (this.telegramBotLanguageService.validateLanguagePackContent(json.getContent())) {
			if (this.telegramBotLanguageService.create(json)) {
				return ResponseEntity.ok().build();
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/get-all")
	public TelegramBotAllLanguagePacksResponse getAll() {
		return this.telegramBotLanguageService.getAllLanguagePacks();
	}

	@GetMapping("/get-content")
	public String getContent(@RequestParam String languagePackName) {
		return this.telegramBotLanguageService.getContent(languagePackName);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody TelegramBotUpdateLanguagePackRequest json) {
		if (this.telegramBotLanguageService.validateLanguagePackContent(json.getNewContent())) {
			this.telegramBotLanguageService.update(json);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String languagePackName) {
		if (!languagePackName.isEmpty()) {
			this.telegramBotLanguageService.delete(languagePackName);
		}

		return ResponseEntity.badRequest().build();
	}
}