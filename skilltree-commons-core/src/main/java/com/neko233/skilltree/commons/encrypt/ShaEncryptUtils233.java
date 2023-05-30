package com.neko233.skilltree.commons.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-系列 广泛应用于密码学、数字签名、数字证书等领域，被认为是目前安全性较高的哈希算法之一。
 *
 * @author SolarisNeko on 2023-05-02
 */
public class ShaEncryptUtils233 {

    /**
     * SHA-1 是 SHA算法家族的较早版本，而SHA-256是更高级和更安全的版本。
     *
     * @param input 输入
     * @return sha1 hash string
     */
    public static String sha1(String input) {
        return hash(input, "SHA-1");
    }

    /**
     * SHA-256是更高级和更安全的版本。SHA-256 是在 2001 年发布的。
     *
     * @param input 输入
     * @return sha256 hash string
     */
    public static String sha256(String input) {
        return hash(input, "SHA-256");
    }

    private static String hash(String input,
                               String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
