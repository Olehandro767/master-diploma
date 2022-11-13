package ua.edu.ontu.service.admin_server_app.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

	ADMIN("admin");

	@Getter
	private final String lowercaseName;

	public static Role[] getAllRoles() {
		return new Role[] { ADMIN };
	}
}