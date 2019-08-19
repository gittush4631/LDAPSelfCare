package com.tushar.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.Enumeration;
import com.tushar.ldap.LdapConnection;
public class LDAPOperations {

    public static void main(String[] args) throws NamingException {
       LdapConnection lc= new LdapConnection();
        System.out.println("Checking if user is authenticated");
        Logger logger = Logger.getLogger(LDAPOperations.class);
        BasicConfigurator.configure();
        logger.info("This is my first log4j's statement");


        //DirContext ctx=lc.ConnectToLDAPAsUser("Sanford", "P@ssw0rd1");
        DirContext ctx2=lc.connectToLDAPAsAdmin();
        searchLDAP(ctx2);
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

    public static void changePassword(DirContext ctx) throws NamingException {
        ctx.modifyAttributes("uid=Sanford,ou=demo,o=tushar",DirContext.REPLACE_ATTRIBUTE,
                new BasicAttributes("userPassword", "P@ssw0rd2"));
        System.out.println("Password changed");
    }
}