package email.services.file;

import java.io.File;

public class FileManager {
    private File file;
    private AsymmetricFileEncrypt asymmetricFileEncrypt = new AsymmetricFileEncrypt();

    public File encryptFile(String clientMail, String senderMail) {
        this.file = asymmetricFileEncrypt.encryptFile(file, clientMail, senderMail);
        return this.file;
    }

    public void decryptFile() {
        asymmetricFileEncrypt.decryptFile(file);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
