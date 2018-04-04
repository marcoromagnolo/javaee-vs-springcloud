package jeevsspring.spring.backoffice.player.service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashPassword {

    private final static Logger logger = Logger.getLogger(HashPassword.class.toString());

    public static String hash(String password) {
        String hash = "";
        try {
            // MD5 Hash of Password
            byte[] digest = MessageDigest.getInstance("MD5").digest(password.getBytes());
            hash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        logger.log(Level.FINE, "login() hash: " + hash + ")");
        return hash;
    }
}
