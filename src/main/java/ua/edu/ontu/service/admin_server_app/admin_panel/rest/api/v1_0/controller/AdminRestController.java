package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.common.SignInRequest;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.response.common.SignInResponse;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.service.AdminService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/admin")
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/check-session")
    public ResponseEntity<SignInResponse> checkSession(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        var result = this.adminService.checkToken(authorizationHeader);

        if (result.isValid()) {
            return ResponseEntity.ok(new SignInResponse(this.adminService.generateToken(result.login())));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest json) {
        var adminResult = this.adminService.checkAdmin(json);

        if (adminResult.isValid()) {
            return ResponseEntity.ok(new SignInResponse(this.adminService.generateToken(json.getLogin())));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}