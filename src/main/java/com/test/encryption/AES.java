package com.test.encryption;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {
    private SecretKey secretKey;
    private final int KEY_SIZE = 256;
    private Cipher encryptCipher;

    public AES() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE);
        secretKey = keyGenerator.generateKey();

    }

    public String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] messageBytes = plainText.getBytes();
        encryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = encryptCipher.doFinal(messageBytes);

        return encode(bytes);
    }

    public String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] encryptedBytes = decode(encryptedText);
        Cipher decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, encryptCipher.getIV());
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] bytes = decryptCipher.doFinal(encryptedBytes);
        return new String(bytes);
    }

    private String encode(byte[] byes){
        return Base64.getEncoder().encodeToString(byes);
    }

    private byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args)throws Exception {
        AES aes = new AES();
        String encryptedText = aes.encrypt("Suman");
        System.out.println("encryptedText = " + encryptedText);
        String decryptedText = aes.decrypt(encryptedText);
        System.out.println(decryptedText);
    }
}
