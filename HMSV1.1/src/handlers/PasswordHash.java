package handlers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {

    /**
     * Hashes the input string using the SHA-256 hash algorithm.
     *
     * @param data The input string to be hashed.
     * @return The hashed string.
     */
    public static String hash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
