package sintesis.text2me.services;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

// Classe que xifra / desxifra els missatges

@Service
public class CryptoService {

	public SecretKey generateAesKey() throws NoSuchAlgorithmException {
		KeyGenerator kG = KeyGenerator.getInstance("AES");
		kG.init(256);
		return kG.generateKey();
	}
	
	
	public String encryptMessage(String message, SecretKey sK, IvParameterSpec iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sK, iv);
		byte[] encrypted = cipher.doFinal(message.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public String decryptMessage(String encrypted, SecretKey sK, IvParameterSpec iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, sK, iv);
		byte[] messageEncrypted = Base64.getDecoder().decode(encrypted);
		byte[] messageDecrypted = cipher.doFinal(messageEncrypted);
		return new String (messageDecrypted);
	}
	
	public IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}
	
	
}
