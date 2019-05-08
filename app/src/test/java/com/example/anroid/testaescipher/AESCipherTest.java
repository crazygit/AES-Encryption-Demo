package com.example.anroid.testaescipher;

import org.junit.Test;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AESCipherTest {

    @Test
    public void encrypt_string() {
        try {
            String key = "this is my key";
            String plainMessage = "Hello World";
            AESCipher aesCipher = AESCipher.getInstance(key);
            String pythonEncryptedMessage = "d19laxPfz8dc+2oJSLKx5byD18ET3wAs/gbZsXEGjmw=";
            String androidEncryptedMessage = aesCipher.encrypt_string(plainMessage);
            System.out.println("Android Encrypted Message: " + androidEncryptedMessage);
            System.out.println("Python  Encrypted Message: " + pythonEncryptedMessage);
            String androidDecryptMessage = aesCipher.decrypt_string(androidEncryptedMessage);
            String pythonDecryptMessage = aesCipher.decrypt_string(pythonEncryptedMessage);
            System.out.println("Origin Plain String: " + plainMessage);
            System.out.println("Android decrypt Message: " + androidDecryptMessage);
            System.out.println("Python decrypt Message: " + pythonDecryptMessage);
        } catch (
                InvalidKeyException e) {
            e.printStackTrace();
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (
                NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (
                IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (
                BadPaddingException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

}