package hr.fer.gymtracker.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPasswordUtil {

    /**
     * Converts byte array to hexadecimal string.
     * @param bytes byte array
     * @return hexadecimal string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * Converts hexadecimal string to byte array.
     * @param hex hexadecimal string
     * @return byte array
     */
    public static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    /**
     * Checks if given hash matches the hash of the password with the given salt.
     * @param password Password
     * @param passwordHash Hash of the password
     * @param passwordSalt Salt of the password
     * @return True if the hash matches, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkPassword(String password, String passwordHash, String passwordSalt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(hexToBytes(passwordSalt));
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash).equals(passwordHash);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    /**
     * Generates a random salt with secure random.
     * @return salt in 16 byte hexadecimal string
     */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * Hashes the password with the given salt using SHA-256.
     * @param password Password
     * @param salt Salt
     * @return Hash of the password
     */
    public static String hashPassword(final String password, final String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(hexToBytes(salt));
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Checks if the email is in the correct format using regex.
     * @param email Email
     * @return True if the email is in the correct format, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkEmailPattern(String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }
}
