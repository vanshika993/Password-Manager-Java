public class Credential {
    String website;
    String username;
    String encryptedPassword;

    // A constructor makes creating new credential objects much easier
    public Credential(String website, String username, String encryptedPassword) {
        this.website = website;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }
}