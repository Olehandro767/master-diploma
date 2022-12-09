package ua.edu.ontu.service.admin_server_app.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class EncryptionUtil {

	private final String initializationVectorString;
	@Getter
	private final String adminSecretKey;
	private final String algorithm = "AES";
	public final String ALGORITHM = "AES/CBC/PKCS5Padding";

	public EncryptionUtil(@Value("${server.security.initialization-vector}") String initializationVectorString,
			@Value("${server.security.admin-secret-key}") String adminSecretKey) {
		this.initializationVectorString = initializationVectorString;
		this.adminSecretKey = adminSecretKey;
	}

	public SecretKey generateKey(int number) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm);
		keyGenerator.init(number);
		return keyGenerator.generateKey();
	}

	public SecretKey getKeyFromPassword(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), this.algorithm);
	}

	public IvParameterSpec generateInitializationVector() {
		return new IvParameterSpec(this.initializationVectorString.getBytes());
	}

	public String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(plainText);
	}

	public String encryptMoreEasy(String password, String privateKey) throws InvalidKeyException,
			NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException,
			IllegalBlockSizeException, InvalidKeySpecException {
		return this.encrypt(this.ALGORITHM, password, this.getKeyFromPassword(password, privateKey),
				this.generateInitializationVector());
	}
}