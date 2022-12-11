package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.dto;

import ua.edu.ontu.service.admin_server_app.database.model.admin.Administrator;

public record CheckAdminResult(Administrator administrator, boolean isValid) {
}