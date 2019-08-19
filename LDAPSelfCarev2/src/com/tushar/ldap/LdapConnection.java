package com.tushar.ldap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

public class LdapConnection {
    static String url = "ldap://10.209.12.39:389";

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
            String INITIAL_CONTEXT_FACTORY = env.getProperty("INITIAL_CONTEXT_FACTORY");
            String PROVIDER_URL= env.getProperty("PROVIDER_URL");
            String SECURITY_AUTHENTICATION=env.getProperty("SECURITY_AUTHENTICATION");
            String SECURITY_PRINCIPAL=env.getProperty("SECURITY_PRINCIPAL");
            String SECURITY_CREDENTIALS=env.getProperty("SECURITY_CREDENTIALS");
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
            //FileReader fileReader=new FileReader("src/LdapCon.properties");
            String filename="LdapCon.properties";

            InputStream input = null;
             
            input=LdapConnection.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);

            }
            Properties env = new Properties();
            env.load(input);
            System.out.println("with key : "+env.getProperty("PROVIDER_URL"));
            String INITIAL_CONTEXT_FACTORY = env.getProperty("INITIAL_CONTEXT_FACTORY");
            String PROVIDER_URL= env.getProperty("PROVIDER_URL");
            String SECURITY_AUTHENTICATION=env.getProperty("SECURITY_AUTHENTICATION");
            String SECURITY_PRINCIPAL=env.getProperty("SECURITY_PRINCIPAL");
            String SECURITY_CREDENTIALS=env.getProperty("SECURITY_CREDENTIALS");
            System.out.println("Context factory = "+INITIAL_CONTEXT_FACTORY);
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
             
        try {    
        	System.out.println("Inside Connect to ldap as user");
    	 String filename="LdapCon.properties";

         InputStream input = null;
          
         input=LdapConnection.class.getClassLoader().getResourceAsStream(filename);
         if(input==null){
             System.out.println("Sorry, unable to find " + filename);

         }
         Properties env = new Properties();
         env.load(input);
         System.out.println("with key : "+env.getProperty("PROVIDER_URL"));
         String INITIAL_CONTEXT_FACTORY = env.getProperty("INITIAL_CONTEXT_FACTORY");
         String PROVIDER_URL= env.getProperty("PROVIDER_URL");
         String SECURITY_AUTHENTICATION=env.getProperty("SECURITY_AUTHENTICATION");
         String SECURITY_PRINCIPAL=user;
         String SECURITY_CREDENTIALS=password;
            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.setProperty(Context.PROVIDER_URL, PROVIDER_URL);
            env.setProperty(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
            env.setProperty(Context.SECURITY_PRINCIPAL, user);
            env.setProperty(Context.SECURITY_CREDENTIALS, password);
        
          System.out.println("Security principal = "+SECURITY_PRINCIPAL);
          System.out.println("Security cred = "+SECURITY_CREDENTIALS);
            DirContext ctx = new InitialDirContext(env);
            System.out.println("connected as user");
            System.out.println(ctx.getEnvironment());
            return  ctx;

        }catch (Exception e) {
        	e.printStackTrace();
		}
        return null;
    }
}





