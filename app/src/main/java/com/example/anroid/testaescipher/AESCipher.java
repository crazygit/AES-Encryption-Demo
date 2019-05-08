package com.example.anroid.testaescipher;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

    private static final String characterEncoding = "UTF-8";
    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private static final String aesEncryptionAlgorithm = "AES";
    private static final String messageDigestAlgorithm = "SHA-256";
    private static final int ivSize = 16;
    private static byte[] keyBytes;

    private static AESCipher instance = null;


    AESCipher(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance(messageDigestAlgorithm);
            md.update(key.getBytes(characterEncoding));
            keyBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static AESCipher getInstance(String key) {
        if (instance == null) {
            instance = new AESCipher(key);
        }

        return instance;
    }

    public String encrypt_string(final String plainMessage) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return Base64.encodeToString(encrypt(plainMessage.getBytes()), Base64.DEFAULT);
    }

    public String decrypt_string(final String encryptedMessage) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes = decrypt(Base64.decode(encryptedMessage, Base64.DEFAULT));
        return new String(encryptedBytes);
    }


    public byte[] encrypt(byte[] plainMessage)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, aesEncryptionAlgorithm);

        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[ivSize];
        random.nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance(cipherTransformation);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] inputBytes = new byte[ivBytes.length + plainMessage.length];
        System.arraycopy(ivBytes, 0, inputBytes, 0, ivBytes.length);
        System.arraycopy(plainMessage, 0, inputBytes, ivBytes.length, plainMessage.length);
        return cipher.doFinal(inputBytes);
    }

    public byte[] decrypt(byte[] encryptMessage)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {

        byte[] ivBytes = Arrays.copyOfRange(encryptMessage, 0, ivSize);
        byte[] inputBytes = Arrays.copyOfRange(encryptMessage, ivSize, encryptMessage.length);

        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, aesEncryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(inputBytes);
    }
}
