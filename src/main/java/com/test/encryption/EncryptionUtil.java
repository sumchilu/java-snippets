package com.test.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    /**
     * Generates a secret key for AES encryption.
     *
     * @return a base64 encoded secret key
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // 192 and 256 bits may not be available
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Encrypts the given data using the provided secret key.
     *
     * @param data      the data to be encrypted
     * @param secretKey the base64 encoded secret key
     * @return the encrypted data as a base64 encoded string
     * @throws Exception if encryption fails
     */
    public static String encrypt(String data, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given encrypted data using the provided secret key.
     *
     * @param encryptedData the base64 encoded encrypted data
     * @param secretKey     the base64 encoded secret key
     * @return the decrypted data
     * @throws Exception if decryption fails
     */
    public static String decrypt(String encryptedData, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        // Generate a secret key
        String secretKey = generateKey();
        System.out.println("Generated Secret Key: " + secretKey);

        // Data to be encrypted
        String data = "Sensitive Information";

        // Encrypt the data
        String encryptedData = encrypt(data, secretKey);
        System.out.println("Encrypted Data: " + encryptedData);

        // Decrypt the data
        String decryptedData = decrypt(encryptedData, secretKey);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}

