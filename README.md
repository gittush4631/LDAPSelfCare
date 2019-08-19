# LDAPSelfCare
LDAP Password change app for most of the LDAP implementation like IBM SDS, AD

This app supports password change for a user by self and change password to new password.
It takes old password for validation of current user and changes the password if the aclentry allows the user to change its own password.


Pull the repository in eclipse. Install tomcat server and you are ready to run the project

There is LdapCon.properties file in /src. Change it as per your need
INITIAL_CONTEXT_FACTORY = com.sun.jndi.ldap.LdapCtxFactory
PROVIDER_URL= ldap://<ldaphostname>:<port>
SECURITY_AUTHENTICATION=simple
SECURITY_PRINCIPAL=cn=root
SECURITY_CREDENTIALS=<password>
BASE_DN=<base dn of the ldap instance>
USER_SEARCH_ATT=<uid or cn or any other user search att you use>
  
  
Run the project on the Tomcat server and you can access it on :

http://localhost:8080/LDAPSelfCarev2


