public class AESUtil{

    public static String encrypt(String password)
    {  String reversed="";
        for(int i=password.length()-1; i>=0;i--)
        {
            reversed+=password.charAt(i);
             
        }
        return reversed; 
    }
    public static String decrypt(String encrypt)
    {
        String original="";
        for(int i=encrypt.length()-1;i>=0;i--)
        {
            original+=encrypt.charAt(i);
        }
        return original;
    }
   
}
