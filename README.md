# Servlet Cocepts and Demo Code

## Servlet Introduction

A servlet is a small Java program that runs within a Web server. Servlets receive and respond to requests from Web clients, usually across HTTP, the HyperText Transfer Protocol.

## Configure Tomcat in Eclipse

1. Install and open Eclipse
2. Configure Tomcat server in Eclipse  
    download Tomcat from https://tomcat.apache.org/download-80.cgi  
    Click on server tab in Eclipse  
    Click on Add new server  
    Select Apache and version and in this case version 8.5
    Click on next and select your downloaded tomcat in your folder  
    In case of no error click Finish
3. Verify the server by click on start

## Creating Web Project in Eclipse

1. Click new project. Select Dynamic Web Project
2. Enter Project name
3. Select generate web.xmldeployment descriptor
4. Finish
5. Right click and select create new html page
6. Add form with two input text box and action

        <!DOCTYPE html>
        <html>
        <head>
        <meta charset="ISO-8859-1">
        <title>Add Numbers Form</title>
        </head>
        <body>
        <form name="addForm" action="add">
            Enter first number: <input type="text" name = "num1"><br><br>
            Enter Second number: <input type="text" name = "num2"><br><br>
            <input type="submit" value="Submit">
        </form>

        </body>
        </html>
7. Run the project as a server.

## Create Servlet & Web.xml
1. Create Servlet
    Right click pn project. Select Class. Enter class name  
    Add "extends HttpServlet" to make your class servlet class  
    Add and implement service method to your servlet class with request and response object  
    HttpServletRequest req and HttpServletResponse resp  
    
###### Code
        package com.dileep;

        import java.io.IOException;
        import java.io.PrintWriter;

        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

        public class AddServlet extends HttpServlet {

            private static final long serialVersionUID = 1L;

            public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

                int num1 = Integer.parseInt(req.getParameter("num1"));
                int num2 = Integer.parseInt(req.getParameter("num2"));

                int sum = num1 + num2;

                PrintWriter out = resp.getWriter();

                out.println("sum of the numbers is: "+sum);

            }

        }


2. Open web.xml file in WEB-INF floder and add the servlet and servlet-mapping
###### Code
        <servlet>
            <servlet-name>addNumber</servlet-name>
            <servlet-class>com.dileep.AddServlet</servlet-class>
        </servlet>

        <servlet-mapping>
            <servlet-name>addNumber</servlet-name>
            <url-pattern>/add</url-pattern>
        </servlet-mapping>

3. Restart the server and test the html form with two numbers.

## GET & POST Method

In place of service() method we can write two menthos doGet():will only accept GET requests and doPost(): will accept only POST requests  
Before calling doGet or doPost Servlet will first call service() method and then from the service method these two methods will be called.

## RequestDispatcher | Calling a Servlet from Servlet

Calling the Servlet from another Servlet
1. Create one more Servlet squareServlet
2. add the entry in web.xml file
3. Add code in addServlet to call the squareServlet
###### Code        
        req.setAttribute("sum", sum);
		
		RequestDispatcher rd = req.getRequestDispatcher("square");
		rd.forward(req, resp);
4. Restart and test the form

## sendRedirect | URL Rewriting

###### difference in SendRedirect() and RequestDispatcher() in Servlet
From: https://www.jitendrazaa.com/blog/java/servlet/difference-in-sendredirect-and-requestdispatcher-in-servlet/

###### SendRedirect

This is the method of object HttpServlerResponse.  
Request is redirected to client (Browser), and it will process the new URL.  
End User can see on which page, url is redirected.  
In Nutshell, Processing done at client side.  


###### RequestDispatcher

This object can be accessed from HttpServletRequest.  
Servlet will internally forward the request to another servlet or jsp page.  
End user don’t know that which page is processed internally.  
In Nutshell, Processing done at server side.

###### Code with URL Rewriting

	resp.sendRedirect("square?sum="+sum);
	
## HttpSession | Cookie

Save the value in seesion using HttpSession or Cookie

###### Code for HttpSession
	
	AddServlet.java
	===============
	
	HttpSession session = req.getSession();
	session.setAttribute("sum", sum);
	resp.sendRedirect("square");
	
	SquareServlet.java
	==================
	
	HttpSession session = req.getSession();	
	int sum = (int) session.getAttribute("sum");
	
###### Code for Cookie

	AddServlet.java
	===============
	
	Cookie cookie = new Cookie("sum", ""+sum);
	resp.addCookie(cookie);
	resp.sendRedirect("square");
	
	SquareServlet.java
	==================
	
	Cookie cookies [] = req.getCookies();
	for(Cookie cookie: cookies) {
		if(cookie.getName().equals("sum"))
			sum = Integer.parseInt(cookie.getValue());
	}
	
## ServletConfig & ServletContext

###### Difference between ServletConfig and ServletContext in Java Servlet

ServletConfig and ServletContext, both are objects created at the time of servlet initialization  
and used to provide some initial parameters or configuration information to the servlet.   
But, the difference lies in the fact that information shared by ServletConfig is for a specific servlet,  
while information shared by ServletContext is available for all servlets in the web application.

###### Code for ServletContext

	web.xml
	===============
	
	<servlet>
		<servlet-name>MyServlet</servlet-name>
		<servlet-class>com.dileep.MyServlet</servlet-class>

		<init-param>
			<param-name>name</param-name>
			<param-value>Dileep Dail</param-value>
		</init-param>
	</servlet>

	<servlet-mapping>
		<servlet-name>MyServlet</servlet-name>
		<url-pattern>/myServlet</url-pattern>
	</servlet-mapping>

	<context-param>
		<param-name>name</param-name>
		<param-value>Dileep</param-value>
	</context-param>
	
	MyServlet.java
	==================
	
	package com.dileep;

	import java.io.IOException;
	import java.io.PrintWriter;

	import javax.servlet.ServletConfig;
	import javax.servlet.ServletContext;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	public class MyServlet extends HttpServlet {

		private static final long serialVersionUID = 1L;

		public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

			//Common for all the Servlets
			ServletContext serCont = getServletContext();

			String name = serCont.getInitParameter("name");

			PrintWriter out = resp.getWriter();
			out.println("Hello "+name);

			//Specific to this servlet
			ServletConfig serConfg = getServletConfig();

			String fullName = serConfg.getInitParameter("name");

			out.println("Hello "+fullName);
		}

	}

## Servlet Annotation Configuration

Remove the servlet configuration from web.xml  
And add @WebServlet("/nameOfServlet") annotation to the Servlet class



# JSP Cocepts and Demo Code

## JSP Introduction

###### https://www.geeksforgeeks.org/introduction-to-jsp/
* It stands for Java Server Pages.
* It is a server side technology.
* It is used for creating web application.
* It is used to create dynamic web content.
* In this JSP tags are used to insert JAVA code into HTML pages.
* It is an advanced version of Servlet Technology.
* It is a Web based technology helps us to create dynamic and platform independent web pages.
* In this, Java code can be inserted in HTML/ XML pages or both.
* JSP is first converted into servlet by JSP container before processing the client’s request.

## How JSP Translated into Servlet?

The JSP engine loads the JSP page from disk and converts it into a servlet content. This conversion is very simple in which all template text is converted to println() statements and all JSP elements are converted to Java code. This code implements the corresponding dynamic behavior of the page.

There are four main section in JSP page:

1. Directive:  

<%@ page import="java.util.Date" %> converts to import in Servlet

2. Declaration:

<%!     
int i = 0;  
}  

converts to class variable in Servlet

3. Scriptlet:

<% int x = i %> converts into content of Servlet service method

4. Expression

<%=x%> convert into servlet out.println("x")










    
            
    

    
