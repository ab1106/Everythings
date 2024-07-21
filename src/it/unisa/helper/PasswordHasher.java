package it.unisa.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PasswordHasher {
public static String hashPassword(String password) {

    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-512"); 
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }

        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    return null; 
}

public static String randGenerator() {
	SecureRandom random = new SecureRandom();
	byte[] bytes = new byte[20];
	random.nextBytes(bytes);
	return new BigInteger(1, bytes).toString(16);
}
}