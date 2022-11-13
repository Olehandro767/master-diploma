package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.service;

import org.springframework.stereotype.Service;

@Service(value = "session_service_v1.0")
public class SessionService {

	public boolean authorizationHeaderIsValid(String authorizationHeaderValue) {
		return false;
	}
}