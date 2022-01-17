package javakit.minhphuc.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class MyHash {

    // LEARN MORE: https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    // Not Recommended algorithm: SHA-1-128-256-512, MD5
    // Recommended algorithm: PBKDF2, Bcrypt, Scrypt
    // Best algorithm: Argon2 -  the latest password hashing contest winner( 2019 )


    // Hash SHA-512.
    // SHA-512 represents the longest key in the third generation of the algorithm.
    // When employed with salt, SHA-512 is still a fair option
    // but there are stronger and slower options out there.
    // ==> Not Recommended
    public static String hashSHA512(String password){
        String passHash = null;
        try {
            byte[] salt = getSalt (16);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // add salt to input
            md.update(salt);
            // generate the hashed password
            // why convert password into array
            // cause String is immutable => can't overwrite
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            // convert bytes into a string
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(
                        Integer.toString(
                                (aByte & 0xff) + 0x100,
                                16
                        ).substring(1)
                );
            }
            passHash = sb.toString();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        return passHash;
    }

    //MD5: is fast algorithm and therefore useless against brute-force attacks
    // that's the hacker compare with rainbow tables
    // ==> Not Recommended

    public static String hashMD5(String password){
        StringBuilder generatePassword = null;
        try {
            byte[] salt = getSalt(16);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] msgPass = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Way1:

//            StringBuilder sb = new StringBuilder();
//            for (byte aByte : msgPass) {
//                sb.append(
//                        Integer.toString(
//                                (aByte & 0xff) + 0x100,
//                                16
//                        ).substring(1)
//                );
//            }
//            System.out.println(sb.toString());

            //Way 2:
            //Convert byte array into signum representation
            BigInteger bi = new BigInteger(1, msgPass);

            // Convert message digest into hex value
            generatePassword = new StringBuilder(bi.toString(16));
            while (generatePassword.length() < 32){
                generatePassword.append("0");
            }

            //Way3:
            //return toHex(msgPass);

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        assert generatePassword != null;
        return generatePassword.toString();
    }

    // PBKDF2: implemented using some CPU-intensive algorithms
    // And we can configurable input value to increase strength of password ( Ex: change iteration value )
    // ==> Recommended
    public static String hashPBKDF2(String password){
        String generatePassword = null;
        try{
            // determines how slow the hash function will be
            int iteration = 1000;
            byte[] salt = getSalt(16);
            // because of String is immutable => can't overwrite
            char[] pass = password.toCharArray();

            // This class support password-base encryption(PBE)
            PBEKeySpec spec = new PBEKeySpec(pass, salt, iteration, 64 * 8);
            // This class will instantiate using the PBKDF2WithHmacSHA1 algorithm
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // generate the hashed password
            byte[] msgPass = factory.generateSecret(spec).getEncoded();
            generatePassword = iteration + ":" + toHex(salt) + ":" + toHex(msgPass);

        }catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e){
            e.printStackTrace();
        }

        return generatePassword;
    }


    // convert byte[] to hex
    public static String toHex(byte[] bytes){
        BigInteger bi = new BigInteger(1, bytes);
        String hex = bi.toString(16);

        int paddingLength = (bytes.length * 2) - hex.length();
        if(paddingLength > 0){
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }else {
            return hex;
        }
    }

    // Verify Password
    public static boolean validatePassPBKDF2(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iteration = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] msgPass = fromHex(parts[2]); // can rename = hash

        PBEKeySpec spec = new PBEKeySpec(
                originalPassword.toCharArray(),
                salt,
                iteration,
                msgPass.length * 8
        );
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] temp = factory.generateSecret(spec).getEncoded();

        // compare bytesHash
        int diff = msgPass.length ^ temp.length;
        for (int i = 0; i < msgPass.length && i < temp.length; i++){
            diff |= msgPass[i] ^ temp[i];
        }
        return diff == 0;
    }

    public static byte[] fromHex(String hex){
        // check from toHex() method
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),16);
        }
        return bytes;
    }


    // set salt for hashing
    // this is a random sequence that is generated for each new hash
    // append salt into hash help for stronger hash password
    public static byte[] getSalt(int valueSalt)
                 throws NoSuchProviderException,NoSuchAlgorithmException {
        // use SecureRandom ( randomness ) we increase the hash's entropy
        SecureRandom random = SecureRandom.getInstance(
                "SHA1PRNG", // Algorithm:  used as a cryptographically strong pseudo-random number
                "SUN" // Provider
        );
        byte[] salt = new byte[valueSalt];
        random.nextBytes(salt);
        return salt;
    }


}
