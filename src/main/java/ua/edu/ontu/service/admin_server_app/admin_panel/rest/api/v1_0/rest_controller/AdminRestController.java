package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.rest_controller;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.common.SignInRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.response.common.SignInResponse;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.service.AdminService;
import ua.edu.ontu.service.admin_server_app.database.service.SessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/admin")
public class AdminRestController {

	private final AdminService adminService;
	private final SessionService sessionService;

	@GetMapping("/check-session")
	public ResponseEntity<SignInResponse> checkSession(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		var result = this.adminService.checkToken(authorizationHeader);

		if (Objects.nonNull(result)) {
			return ResponseEntity.ok(new SignInResponse(this.adminService.generateToken(result.login())));
		}

		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/sign-in")
	public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest json) {
		var adminResult = this.adminService.checkAdmin(json);

		if (adminResult.isValid()) {
			String token = this.adminService.generateToken(json.getLogin());
			this.sessionService.addNewToken(token);
			return ResponseEntity.ok(new SignInResponse(token));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}