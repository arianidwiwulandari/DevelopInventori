package kel2.ddplc.inventoryfaris;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Alpha on 28/11/2016.
 */

class Encryption {
    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;


    public Encryption(String password) throws Exception
    {
        // hash password with SHA-256 and crop the output to 128-bit for key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }

    public AlgorithmParameterSpec getIV()
    {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    public String encrypt(String plainText) throws Exception
    {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(Charset.forName("UTF8")));
            String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), Charset.forName("UTF8"));
            return encryptedText;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Encryption error for " + plainText, e);
        }
        return "";
    }

//    public String decrypt(String cryptedText) throws Exception
//    {
//        try {
//            cipher.init(Cipher.DECRYPT_MODE, key, spec);
//            byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
//            byte[] decrypted = cipher.doFinal(bytes);
//            String decryptedText = new String(decrypted, Charset.forName("UTF8"));
//            return decryptedText;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("Error", "Decipher error for " + cryptedText, e);
//        }
//        return "";
//    }
}
