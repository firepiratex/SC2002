package handlers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Provides a method for hashing passwords using the SHA-256 algorithm.
 * This class is used to enhance security by storing hashed passwords instead of plain text.
 */
public class PasswordHash {

    /**
     * Hashes the input string using the SHA-256 hash algorithm.
     *
     * @param data The input string to be hashed.
     * @return The hashed string in hexadecimal format, or null if an exception occurs.
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
