package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.request.common;

import lombok.Data;

@Data
public class SignInRequest {

	private String login;
	private String password;
}