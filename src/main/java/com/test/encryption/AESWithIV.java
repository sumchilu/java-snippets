package com.test.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESWithIV {
    public static void main(String[] args) throws Exception {
        // Generate a random IV
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);

        // Generate a secret key
        byte[] key = "mysecretkey1234".getBytes();

        // Create a SecretKeySpec and IvParameterSpec
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Encrypt data
        String data = "Sensitive Information";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        // Store the IV and encrypted data
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData);

        // Decrypt data
        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(Base64.getDecoder().decode(ivBase64)));
        byte[] decryptedData = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedDataBase64));

        System.out.println("Decrypted Data: " + new String(decryptedData));
    }
}

