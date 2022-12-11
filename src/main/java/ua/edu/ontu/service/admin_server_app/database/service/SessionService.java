package ua.edu.ontu.service.admin_server_app.database.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ua.edu.ontu.service.admin_server_app.util.JwtUtil;

@Service
public class SessionService {

	private final JwtUtil jwtUtil;
	private final Map<String, LocalDate> cache = new HashMap<>();

	public SessionService(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	// TODO redis
	public boolean authorizationHeaderIsValid(String authorizationHeaderValue) {
		var localDateNow = LocalDate.now();

		for (var entry : this.cache.entrySet()) {
			if (localDateNow.isAfter(entry.getValue())) {
				this.cache.remove(entry.getKey());
			}
		}

		return Objects.nonNull(this.cache.get(authorizationHeaderValue));
	}

	public void addNewToken(String token) {
		this.cache.put(this.jwtUtil.getJWT_START_KEY_PART() + token, LocalDate.now());
	}
}