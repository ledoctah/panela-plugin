package dev.matheusferreira.panelaplugin.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class HashProvider {
    private final String salt = "default";

    public String generateHash(String payload) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] payloadCharArray = payload.toCharArray();

        byte[] saltBytes = this.salt.getBytes(StandardCharsets.UTF_8);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(payloadCharArray, saltBytes, 1000, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hashBytes = skf.generateSecret(pbeKeySpec).getEncoded();

        return toHex(hashBytes);
    }

    public boolean compareHash(String hashed, String payload) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] payloadCharArray = payload.toCharArray();

        byte[] hash = fromHex(hashed);
        byte[] saltBytes = this.salt.getBytes(StandardCharsets.UTF_8);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(payloadCharArray, saltBytes, 1000, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(pbeKeySpec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
