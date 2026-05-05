import java.util.ArrayList;

public class PasswordManager{
    ArrayList<Credential> list =new ArrayList<>();
    public void addPassword(String website,String username,String password)
    {  String encrypasswrd= AESUtil.encrypt(password);
        Credential c=new Credential();
        c.website=website;
        c.username=username;
        c.encryptedPassword =encrypasswrd;
        list.add(c);
        
    }
    public ArrayList<Credential> getAllPasswords() {
         return list;
      }
    public String getDecryptedPassword(String encrypted) {
       return AESUtil.decrypt(encrypted);
}
    public void deletePassword(String website)
    {
      for(int i=0;i<list.size();i++)
      { Credential c =list.get(i);
         if (c.website.equals(website))
         {
            list.remove(i);
            break;
         }
      }
    }
}