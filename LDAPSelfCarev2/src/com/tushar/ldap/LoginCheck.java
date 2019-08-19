package com.tushar.ldap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidAttributeValueException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
     	 Logger logger = Logger.getLogger(LoginCheck.class);
	        BasicConfigurator.configure();
	        logger.info("Start");
		response.setContentType("text/html");  
		


	    PrintWriter out = response.getWriter();  
	          
	    String n=request.getParameter("username");  
	    String p=request.getParameter("userpass");  
	    String newpass=request.getParameter("password");
	    
	    HttpSession session=request.getSession(true);
	    logger.info("Session id"+session.getId());
	    
	   logger.info("username :"+n);
	   System.out.println("Pass "+p);
	   System.out.println("new password "+newpass);
	   try {
	   LDAPOperations lop=new LDAPOperations();
	   String finalDN=lop.searchLDAP2(n);
	   System.out.println("Final DN : "+finalDN);
	   String fuserid=finalDN;
	   
	   
	   LdapConnection lc=new LdapConnection();
	  
	   DirContext dc=lc.connectToLDAPAsUser(fuserid, p);
	   System.out.println("Changing password2");
	  //DirContext da=lc.connectToLDAPAsAdmin();
	   dc.modifyAttributes(fuserid,DirContext.REPLACE_ATTRIBUTE,
               new BasicAttributes("userPassword", newpass));
       logger.info("Password changed");
       out.println("<html>");
       out.println("<head>");
       out.println("<title>Password changed</title>");
       out.println("</head>");
       out.println("<body>");
       out.println("<div class=\"succmsg\">Password changed successfully</div>");
       out.println("</body>");
       out.println("</html>");
       //out.print("Password successfully changed");
       request.getRequestDispatcher("/index.html").include(request, response);
       dc.close();
     //  da.close();
       session.invalidate();
       //sc.getRequestDispatcher("/Success.html").forward(request, response);
       
       
	   }
	   catch(InvalidAttributeValueException e)
	   {
	   logger.info("password in history");
	   //sc.getRequestDispatcher("/Failed.html").forward(request, response);
	  String errmsg=e.getMessage();
	  
	  //System.out.println(errmsg.substring(errmsg.indexOf("Error,")+7,errmsg.length()-1));
	  String finalemsg=errmsg.substring(errmsg.indexOf("Error,")+7,errmsg.length()-1);
	  String outerrmsg="";
	 System.out.println(finalemsg);
	 if(finalemsg.contains("error code 19 - Password contains too few uppercase characters")){
		 outerrmsg="Password contains too few uppercase characters";
	 }
	 else if(finalemsg.contains("error code 19 - Password contains too few special characters")){
		 outerrmsg="Password should contain atleast one special character";
		 }
	 else{
		 outerrmsg=finalemsg;
	 }
	 out.println("<html>");
     out.println("<body>");
     out.println("<div class=\"errmsg\">"+outerrmsg+"</div>");
     out.println("</body>");
     out.println("</html>");
	  
	   
	   request.getRequestDispatcher("/index.html").include(request, response);
	   
	   }catch(javax.naming.NoPermissionException ex) {
		   logger.info("Permission issue");
		   ex.printStackTrace();
		   //sc.getRequestDispatcher("/Failed.html").forward(request, response);
		  /*String errmsg=e.getMessage();
		  System.out.println("Error msg : "+errmsg);
		 if(errmsg.contains("Invalid Credentials")) { }*/
			 out.println("<html>");
		     out.println("<body>");
		     out.println("<div class=\"errmsg\">Insufficient Access Rights</h1></div>");
			 out.println("</body>");
			  out.println("</html>");
		 
		 
			  
		  
		   
		   request.getRequestDispatcher("/index.html").include(request, response);
		   
	   }
	   
	   catch(Exception e) {
		   logger.info("login exception");
		   e.printStackTrace();
		   //sc.getRequestDispatcher("/Failed.html").forward(request, response);
		  /*String errmsg=e.getMessage();
		  System.out.println("Error msg : "+errmsg);
		 if(errmsg.contains("Invalid Credentials")) { }*/
			 out.println("<html>");
		     out.println("<body>");
		     out.println("<div class=\"errmsg\">Current Password is incorrect</h1></div>");
			 out.println("</body>");
			  out.println("</html>");
		 
		 
			  
		  
		   
		   request.getRequestDispatcher("/index.html").include(request, response);
		   
	   }
	   
	   System.out.println("Successful login");
	  
	   
	   
	   
	}

}
