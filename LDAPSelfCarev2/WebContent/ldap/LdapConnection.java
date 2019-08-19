package com.tushar.ldap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.InputStream;
import java.util.Properties;

public class LdapConnection {
    static String url = "ldap://hotspot1:389";

    public DirContext connectToLDAP() {
        try {
           // FileReader fileReader=new FileReader("src/LdapCon.properties");
            String filename="LdapCon.properties";

            InputStream input = null;
            input=LdapConnection.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);

            }
            Properties env = new Properties();
            env.load(input);
            String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
            String PROVIDER_URL= "ldap://192.168.42.65:1389";
            String SECURITY_AUTHENTICATION="simple";
            String SECURITY_PRINCIPAL="uid=Sanford,dc=com";
            String SECURITY_CREDENTIALS="P@ssw0rd1";
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, PROVIDER_URL);
            env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
            env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
            env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);

            
            DirContext ctx = new InitialDirContext(env);
            System.out.println("User Authenticated");
            System.out.println(ctx.getEnvironment());
            return  ctx;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public DirContext connectToLDAPAsAdmin() {
        try {
            // FileReader fileReader=new FileReader("src/LdapCon.properties");
            String filename="LdapCon.properties";

            InputStream input = null;
            input=LdapConnection.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);

            }
            Properties env = new Properties();
            env.load(input);
            String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
            String PROVIDER_URL= "ldap://hotspot1:389";
            String SECURITY_AUTHENTICATION="simple";
            String SECURITY_PRINCIPAL="cn=root";
            String SECURITY_CREDENTIALS="P@ssw0rd";
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, PROVIDER_URL);
            env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
            env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
            env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);


            DirContext ctx = new InitialDirContext(env);
            System.out.println("connected");
            System.out.println(ctx.getEnvironment());
            return  ctx;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DirContext connectToLDAPAsUser(String user,String password) throws NamingException {
             
            
            Properties env = new Properties();
            
            
            String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
            String PROVIDER_URL= "ldap://hotspot1:389";
            String SECURITY_AUTHENTICATION="simple";
            String SECURITY_PRINCIPAL="uid="+user+",ou=demo,o=tushar";
            String SECURITY_CREDENTIALS=password;
            System.out.println("USER "+SECURITY_PRINCIPAL);
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, PROVIDER_URL);
            env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
            env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
            env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);


            DirContext ctx = new InitialDirContext(env);
            System.out.println("connected");
            System.out.println(ctx.getEnvironment());
            return  ctx;


       
    }
}





