import java.util.ArrayList;

public class PasswordManager {
    private ArrayList<Credential> list = new ArrayList<>();

    public void addPassword(String website, String username, String password) {
        String encrypasswrd = AESUtil.encrypt(password);
        Credential c = new Credential(website, username, encrypasswrd);
        list.add(c);
    }

    public ArrayList<Credential> getAllPasswords() {
        return list;
    }

    public String getDecryptedPassword(String encrypted) {
        return AESUtil.decrypt(encrypted);
    }

    public void deletePassword(String website) {
        for (int i = 0; i < list.size(); i++) {
            Credential c = list.get(i);
            if (c.website.equals(website)) {
                list.remove(i);
                break; // Exit the loop once the item is deleted
            }
        }
    }
}