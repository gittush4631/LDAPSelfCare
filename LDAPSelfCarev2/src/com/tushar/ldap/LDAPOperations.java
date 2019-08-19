package com.tushar.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.tushar.ldap.LdapConnection;
public class LDAPOperations {

    public static void main(String[] args) throws NamingException, IOException {
       
        System.out.println("Checking if user is authenticated");
        Logger logger = Logger.getLogger(LDAPOperations.class);
        BasicConfigurator.configure();
        logger.info("This is my first log4j's statement");


        //DirContext ctx=lc.ConnectToLDAPAsUser("Sanford", "P@ssw0rd1");
        LDAPOperations lo=new LDAPOperations();
        lo.searchLDAP2("Clay");
      /*  LdapConnection lc=new LdapConnection();
 	   try {
 		   System.out.println("logging as user");
 	   DirContext dc=lc.connectToLDAPAsUser("uid=clay,ou=demo,o=tushar", "abc");
 	   }
 	   catch (Exception e) {
		e.printStackTrace();
	}*/
       
        //searchLDAP(ctx2);
   //ChangePassword(ctx2);

    }

    public static void searchLDAP(DirContext ctx) throws NamingException {
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attrList = {"dn"};
        constraints.setReturningAttributes(attrList);
        NamingEnumeration results = ctx.search("ou=demo,o=tushar", "(!(pwdChangedTime>=20190327132222.746938Z))", constraints);
        while (results.hasMore()) {
            SearchResult si =(SearchResult)results.next();
            System.out.println(si.getName());
            Attributes attrs = si.getAttributes();
            if (attrs == null) {
                System.out.println("No attributes");
                continue;

            }
            NamingEnumeration ae = attrs.getAll();
            while (ae.hasMoreElements()) {
                Attribute attr =(Attribute)ae.next();
                String id = attr.getID();
                Enumeration vals = attr.getAll();
                while (vals.hasMoreElements())
                    System.out.println("   "+id + ": " + vals.nextElement());

            }
        }
    }

    public  String searchLDAP2(String n) throws NamingException, IOException {
    	
    		System.out.println("Inside search2");
    	LdapConnection lc= new LdapConnection();
    	DirContext ctx=lc.connectToLDAPAsAdmin();
    	String finalDN="";
    	String prefinalDN="";
    	//get property file
    	 String filename="LdapCon.properties";
         InputStream input = null;        
         input=LdapConnection.class.getClassLoader().getResourceAsStream(filename);
         if(input==null){
             System.out.println("Sorry, unable to find " + filename);

         }
         Properties env = new Properties();
         env.load(input);
         String BASE_DN = env.getProperty("BASE_DN");
         String USER_SEARCH_ATT = env.getProperty("USER_SEARCH_ATT");
        // System.out.println(BASE_DN+USER_SEARCH_ATT);
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attrList = {"dn"};
        constraints.setReturningAttributes(attrList);
        NamingEnumeration results = ctx.search(BASE_DN, USER_SEARCH_ATT+"="+n, constraints);
        while (results.hasMore()) {
            SearchResult si =(SearchResult)results.next();
            System.out.println(si.getName());
            
            Attributes attrs = si.getAttributes();
            if (attrs == null) {
                System.out.println("No attributes");
                continue;

            }
            NamingEnumeration ae = attrs.getAll();
            while (ae.hasMoreElements()) {
                Attribute attr =(Attribute)ae.next();
                String id = attr.getID();
                Enumeration vals = attr.getAll();
                while (vals.hasMoreElements())
                    System.out.println("   "+id + ": " + vals.nextElement());

            }
            prefinalDN=si.getName();
            if(prefinalDN.toLowerCase().contains(n.toLowerCase())){
            	finalDN=prefinalDN+","+BASE_DN;
            System.out.println("Result"+finalDN);
            }
        }
        return finalDN;
        
    	
    }  
    
    public static void changePassword(DirContext ctx) throws NamingException {
        ctx.modifyAttributes("uid=Sanford,ou=demo,o=tushar",DirContext.REPLACE_ATTRIBUTE,
                new BasicAttributes("userPassword", "P@ssw0rd2"));
        System.out.println("Password changed");
    }
}