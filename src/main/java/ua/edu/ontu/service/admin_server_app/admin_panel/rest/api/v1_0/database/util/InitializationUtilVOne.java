package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.model.admin.Administrator;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.repo.IAdministratorRepository;

@Slf4j
@RequiredArgsConstructor
public class InitializationUtilVOne {

	private final IAdministratorRepository administratorRepository;

	public void setUpTheFirstLaunchOfTheApplication() throws IOException {
		var adminList = this.administratorRepository.findAll();

		try (var inputStream = new FileInputStream("./resources/admin.inf")) {
			if (adminList.isEmpty()) {
				var properties = new Properties();
				var administrator = new Administrator();
				properties.load(inputStream);
				administrator.setLogin(properties.getProperty("admin_login"));
				administrator.setPassword(properties.getProperty("private_admin_password"));
				administrator.setName("admin");
				this.administratorRepository.save(administrator);
			}
		} catch (IOException exception) {
			log.error("Can't resolve ./resources/admin.inf");
			System.exit(-1);
		}
	}
}