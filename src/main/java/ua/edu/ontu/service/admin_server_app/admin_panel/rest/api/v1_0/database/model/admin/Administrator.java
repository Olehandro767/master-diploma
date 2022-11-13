package ua.edu.ontu.service.admin_server_app.admin_panel.rest.api.v1_0.database.model.admin;

import lombok.Data;
import ua.edu.ontu.service.admin_server_app.util.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Data
@Entity
@Table(name = "admin_table")
public class Administrator {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false, unique = true)
	private String login;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;

	public void setPassword(String password, EncryptionUtil encryptionUtil)
			throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
			NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
		this.password = encryptionUtil.encryptMoreEasy(password, encryptionUtil.getAdminSecretKey());
	}

	public boolean checkPassword(String password, EncryptionUtil encryptionUtil)
			throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
			NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
		return this.password.equals(encryptionUtil.encryptMoreEasy(password, encryptionUtil.getAdminSecretKey()));
	}
}