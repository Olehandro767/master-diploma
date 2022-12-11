package ua.edu.ontu.service.admin_server_app.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.edu.ontu.service.admin_server_app.database.model.admin.Administrator;

public interface IAdministratorRepository extends JpaRepository<Administrator, Long> {

	Administrator findByLogin(String login);
}