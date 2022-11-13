package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.util;

import lombok.RequiredArgsConstructor;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.model.admin.Administrator;
import ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.repo.IAdministratorRepository;
import ua.edu.ontu.service.admin_server_app.util.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
public class InitializationUtilV1_0 {

	private final IAdministratorRepository administratorRepository;
	private final EncryptionUtil encryptionUtil;

	public void setUpTheFirstLaunchOfTheApplication()
			throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
			NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
		var adminList = this.administratorRepository.findAll();

		if (adminList.size() < 1) {
			var administrator = new Administrator();
			administrator.setLogin("admin");
			administrator.setPassword("admin", this.encryptionUtil);
			administrator.setName("admin");
			this.administratorRepository.save(administrator);
		}
	}
}