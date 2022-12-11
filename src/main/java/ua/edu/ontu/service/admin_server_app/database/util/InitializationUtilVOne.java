package ua.edu.ontu.service.admin_server_app.database.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.admin_server_app.database.model.admin.Administrator;
import ua.edu.ontu.service.admin_server_app.database.repo.IAdministratorRepository;

@Slf4j
@RequiredArgsConstructor
public class InitializationUtilVOne {

	private final IAdministratorRepository administratorRepository;

	public void setUpTheFirstLaunchOfTheApplication() throws IOException {
		var adminList = this.administratorRepository.findAll();
		String adminInfPath = "./resources/admin.inf";

		try (var inputStream = new FileInputStream(adminInfPath)) {
			var properties = new Properties();
			properties.load(inputStream);
			String login = properties.getProperty("admin_login");
			String password = properties.getProperty("private_admin_password");

			if (adminList.isEmpty()) {
				var administrator = new Administrator();
				administrator.setLogin(login);
				administrator.setPassword(password);
				administrator.setName("admin");
				this.administratorRepository.save(administrator);
				Files.delete(Paths.get(adminInfPath));
			} else {
				var adminEntity = this.administratorRepository.findByLogin(login);

				if (Objects.isNull(adminEntity)) {
					adminEntity = new Administrator();
					adminEntity.setName(login);
				}

				adminEntity.setLogin(login);
				adminEntity.setPassword(password);
				this.administratorRepository.save(adminEntity);
			}
		} catch (Exception exception) {
			if (adminList.isEmpty()) {
				log.error("Can't resolve " + adminInfPath);
				System.exit(-1);
			} else {
				log.warn("File " + adminInfPath + " is not exists");
			}
		}
	}
}