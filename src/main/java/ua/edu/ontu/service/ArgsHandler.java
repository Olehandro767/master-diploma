package ua.edu.ontu.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.yaml.snakeyaml.Yaml;

import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.admin_server_app.util.EncryptionUtil;

@Slf4j
public class ArgsHandler {

	private boolean argsIsExists = false;
	private final String UPDATE_ADMIN = "--update-admin";

	private String[] getArguments() {
		return new String[] { UPDATE_ADMIN, };
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArgsHandler handleArgsIfExists(String[] args) {
		List<String> arguments = Arrays.asList(this.getArguments());
		var inputStream = this.getClass().getClassLoader().getResourceAsStream("application.yaml");
		var yaml = new Yaml();
		Map<String, Object> yamlMap = yaml.load(inputStream);
		Map<String, Object> server = (Map) yamlMap.get("server");
		Map<String, Object> serverSecurity = (Map) server.get("security");
		String iv = (String) serverSecurity.get("initialization-vector");
		String adminSecretKey = (String) serverSecurity.get("admin-secret-key");
		var encryptionUtil = new EncryptionUtil(iv, adminSecretKey);

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if (arguments.contains(arg)) {
				this.argsIsExists = true;
				try {
					if (arg.equals(this.UPDATE_ADMIN)) {
						var inputStreamReader = new InputStreamReader(System.in);
						var bufferedReader = new BufferedReader(inputStreamReader);
						var fileOutputStream = new FileOutputStream("./resources/admin.inf");
						var adminInfProperties = new Properties();
						System.out.print("admin login: ");
						String login = bufferedReader.readLine();
						System.out.print("admin password: ");
						String password = bufferedReader.readLine();
						bufferedReader.close();
						adminInfProperties.setProperty("admin_login", login);
						adminInfProperties.setProperty("private_admin_password",
								encryptionUtil.encryptMoreEasy(password, encryptionUtil.getAdminSecretKey()));
						adminInfProperties.store(fileOutputStream, null);
						fileOutputStream.close();
					}
				} catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
						| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException
						| InvalidKeySpecException e) {
					log.error(e.getMessage(), e);
				}
				break;
			}
		}

		return this;
	}

	public boolean canContinue() {
		return !this.argsIsExists;
	}
}