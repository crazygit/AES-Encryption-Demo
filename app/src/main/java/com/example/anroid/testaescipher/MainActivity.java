package com.example.anroid.testaescipher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static {
        // 加载C代码库, 库的名称, 必须是CMakeLists.txt中指定的名称
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String key = stringFromJNI();
            String plainMessage = "Hello World";
            AESCipher aesCipher = AESCipher.getInstance(key);
            String pythonEncryptedMessage = "d19laxPfz8dc+2oJSLKx5byD18ET3wAs/gbZsXEGjmw=";
            String androidEncryptedMessage = aesCipher.encrypt_string(plainMessage);
            Log.d(TAG, "Android Encrypted Message: " + androidEncryptedMessage);
            Log.d(TAG, "Python  Encrypted Message: " + pythonEncryptedMessage);
            String androidDecryptMessage = aesCipher.decrypt_string(androidEncryptedMessage);
            String pythonDecryptMessage = aesCipher.decrypt_string(pythonEncryptedMessage);
            Log.d(TAG, "Origin Plain String: " + plainMessage);
            Log.d(TAG, "Android decrypt Message: " + androidDecryptMessage);
            Log.d(TAG, "Python decrypt Message: " + pythonDecryptMessage);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public native String stringFromJNI();
}