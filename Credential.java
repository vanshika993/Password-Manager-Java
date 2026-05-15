public class Credential {
    String website;
    String username;
    String encryptedPassword;

     
    public Credential(String website, String username, String encryptedPassword) {
        this.website = website;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }
}