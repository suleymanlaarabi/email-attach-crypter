package email.services.file;

import java.io.File;
import java.nio.file.Files;

// import for encrypting and decrypting files
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptDecrypt {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String KEY = "T2e1rVN+AqMdClGt3q0gZQX34x/+om7R";

    public static void encryptFile(File file) {
        processFile(Cipher.ENCRYPT_MODE, file);
    }

    public static void decryptFile(File file) {
        processFile(Cipher.DECRYPT_MODE, file);
    }

    private static void processFile(int cipherMode, File file) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            byte[] inputBytes = Files.readAllBytes(file.toPath());
            byte[] outputBytes = cipher.doFinal(inputBytes);

            Files.write(file.toPath(), outputBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
