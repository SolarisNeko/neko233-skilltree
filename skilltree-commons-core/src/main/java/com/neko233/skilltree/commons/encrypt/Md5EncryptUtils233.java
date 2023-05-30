package com.neko233.skilltree.commons.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5已经不再被认为是安全的哈希算法。
 * 在一些安全性要求较高的场景中，应该考虑使用更安全的哈希算法，如 SHA-256。
 *
 * @author SolarisNeko on 2023-05-02
 */
public class Md5EncryptUtils233 {

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有 MD5 算法", e);
        }
    }
}
