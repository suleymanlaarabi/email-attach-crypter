package email.services.gpg;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GpgManager {

  Runtime runtime = Runtime.getRuntime();
  String gpgPath = "\"C:\\Program Files (x86)\\GnuPG\\bin\\gpg\"";

  public GpgManager() {
    // update gpg path according to the system
    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
      gpgPath = "gpg";
    }
  }

  private Process useCustomCommand(String[] args) {
    try {
      return runtime.exec(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void importKey(String keyPath) {
    useCustomCommand(new String[] { gpgPath, "--import", keyPath });
  }

  public void exportKey(String keyId, String path) {
    useCustomCommand(new String[] { gpgPath, "--export", keyId, "--armor", "-o", path });
  }

  public void generateKey(String name, String email, String password) {
    useCustomCommand(
        new String[] { gpgPath, "--batch", "--passphrase", password, "--quick-generate-key", name, email });
  }

  public String getVersions() {
    Process process = useCustomCommand(new String[] { gpgPath, "--version" });
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    try {
      String line = reader.readLine();
      line = line.replaceAll("[^\\d+\\.]", "");
      return line;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "GPG not found";
  }

}
