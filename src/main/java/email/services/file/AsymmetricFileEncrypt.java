package email.services.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AsymmetricFileEncrypt {

    Runtime runtime = Runtime.getRuntime();
    String gpgPath = "\"C:\\Program Files (x86)\\GnuPG\\bin\\gpg\"";

    public AsymmetricFileEncrypt() {
        // update gpg path according to the system
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            gpgPath = "gpg";
        }
    }

    public File encryptFile(File file, String clientMail, String senderMail) {
        if (file.getAbsolutePath().endsWith(".gpg")) {
            return file;
        }
        File newFile = new File(file.getAbsolutePath() + ".gpg");
        if (newFile.exists()) {
            newFile.delete();
        }
        String[] args = new String[] { gpgPath, "--trust-model", "always", "-u", senderMail, "-r", clientMail, "-s",
                "-e",
                file.getAbsolutePath() };
        try {
            Process process = runtime.exec(args);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }

    }

    public File decryptFile(File file) {
        File newFile = new File(file.getAbsolutePath().replace(".gpg", ""));
        try {
            runtime.exec(new String[] { gpgPath, "-o", newFile.getAbsolutePath(), "--trust-model", "always", "-d",
                    file.getAbsolutePath() });
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }

    }

}
