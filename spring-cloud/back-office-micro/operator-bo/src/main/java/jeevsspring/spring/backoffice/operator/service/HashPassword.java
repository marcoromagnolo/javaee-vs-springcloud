package jeevsspring.spring.backoffice.operator.service;

import org.jboss.logging.Logger;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    //JBoss Logger
    private final static Logger logger = Logger.getLogger(HashPassword.class);

    public static String hash(String password) {
        String hash = "";
        try {
            // MD5 Hash of Password
            byte[] digest = MessageDigest.getInstance("MD5").digest(password.getBytes());
            hash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        logger.debug("login() hash: " + hash + ")");
        return hash;
    }
}
