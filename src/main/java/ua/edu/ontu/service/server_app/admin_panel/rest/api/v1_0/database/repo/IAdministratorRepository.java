package ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.model.admin.Administrator;

public interface IAdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findByLogin(String login);
}