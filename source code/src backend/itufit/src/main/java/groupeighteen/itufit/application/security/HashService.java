package groupeighteen.itufit.application.security;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class HashService {

    public byte[] hashPassword(String password, byte[] salt){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return hashedPassword;
    }

    public byte[] saltPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
