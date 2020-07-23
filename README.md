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


## Servlet Filter

*From https://www.javatpoint.com/servlet-filter

A filter is an object that is invoked at the preprocessing and postprocessing of a request.  

It is mainly used to perform filtering tasks such as conversion, logging, compression, encryption and decryption, input validation etc.  

The servlet filter is pluggable, i.e. its entry is defined in the web.xml file, if we remove the entry of filter from the web.xml file,  
filter will be removed automatically and we don't need to change the servlet.

The javax.servlet package contains the three interfaces of Filter API.
1. Filter
2. FilterChain
3. FilterConfig

###### Filter interface provides the life cycle methods for a filter.

* public void init(FilterConfig config)
* public void doFilter(HttpServletRequest request,HttpServletResponse response, FilterChain chain)
* public void destroy()

###### Sample Filter Example

MyFilter.java

	import java.io.IOException;  
	import java.io.PrintWriter;  

	import javax.servlet.*;  

	public class MyFilter implements Filter{  

	public void init(FilterConfig arg0) throws ServletException {}  

	public void doFilter(ServletRequest req, ServletResponse resp,  
	    FilterChain chain) throws IOException, ServletException {  

	    PrintWriter out=resp.getWriter();  
	    out.print("filter is invoked before");  

	    chain.doFilter(req, resp);//sends request to next resource  

	    out.print("filter is invoked after");  
	    }  
	    public void destroy() {}  
	}  
	
HelloServlet.java

	import java.io.IOException;  
	import java.io.PrintWriter;  

	import javax.servlet.ServletException;  
	import javax.servlet.http.*;  

	public class HelloServlet extends HttpServlet {  
	    public void doGet(HttpServletRequest request, HttpServletResponse response)  
		    throws ServletException, IOException {  

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		out.print("<br>welcome to servlet<br>");  
	    }  
	}  
	
web.xml

	<web-app>  
  
	<servlet>  
	<servlet-name>s1</servlet-name>  
	<servlet-class>HelloServlet</servlet-class>  
	</servlet>  

	<servlet-mapping>  
	<servlet-name>s1</servlet-name>  
	<url-pattern>/servlet1</url-pattern>  
	</servlet-mapping>  

	<filter>  
	<filter-name>f1</filter-name>  
	<filter-class>MyFilter</filter-class>  
	</filter>  

	<filter-mapping>  
	<filter-name>f1</filter-name>  
	<url-pattern>/servlet1</url-pattern>  
	</filter-mapping>  

	</web-app>
	
## File Upload in Java Servlet

*Add cos.jar in the project

###### Example Code

FileUploadServlet.java

	package com.dileep;

	import javax.servlet.http.HttpServlet;
	import java.io.*;  
	import javax.servlet.ServletException;  
	import javax.servlet.http.*;  
	import com.oreilly.servlet.MultipartRequest;  

	public class FileUploadServlet extends HttpServlet {

		private static final long serialVersionUID = 1L;

		public void doPost(HttpServletRequest request, HttpServletResponse response)  
			    throws ServletException, IOException {  

			response.setContentType("text/html");  
			PrintWriter out = response.getWriter();  

			MultipartRequest m=new MultipartRequest(request,"c:/Dileep/fulltime");  
			out.print("successfully uploaded");
		}
	}

web.xml

	<servlet>  
		<servlet-name>UploadServlet</servlet-name>  
		<servlet-class>com.dileep.FileUploadServlet</servlet-class>  
	</servlet>  

	<servlet-mapping>  
		<servlet-name>UploadServlet</servlet-name>  
		<url-pattern>/upload</url-pattern>  
	</servlet-mapping>
	
uploadFile.html

	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>File Upload</title>
	</head>
	<body>
		<form action="upload" method="post" enctype="multipart/form-data">  
			Select File:<input type="file" name="fname"/><br/>  
			<input type="submit" value="upload"/>  
		</form>  
	</body>
	</html>

## File Download in Java Servlet

###### Example Code

DownloadServlet.java

	package com.dileep;

	import java.io.*;  
	import javax.servlet.ServletException;   

	public class FileDownloadServlet extends HttpServlet {

		private static final long serialVersionUID = 1L;

		public void doGet(HttpServletRequest request, HttpServletResponse response)  
		    throws ServletException, IOException {  

			response.setContentType("text/html");  
			PrintWriter out = response.getWriter();  

			String filename = "index.html";   
			String filepath = "c:/Dileep/fulltime//";   

			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   

			FileInputStream fileInputStream = new FileInputStream(filepath + filename);  

			int i;   
			while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
			}   
			fileInputStream.close();   
			out.close();   
		}  
	}
  

downloadFile.html

	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>File Download</title>
	</head>
	<body>
		<a href="DownloadServlet">download the file</a>  
	</body>
	</html>
	
web.xml

	<servlet>  
		<servlet-name>DownloadServlet</servlet-name>  
		<servlet-class>com.dileep.FileDownloadServlet</servlet-class>  
	</servlet>  

	<servlet-mapping>  
		<servlet-name>DownloadServlet</servlet-name>  
		<url-pattern>/DownloadServlet</url-pattern>  
	</servlet-mapping>
	
	
	

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

1.  JSP Directive:  

<%@ page import="java.util.Date" %> converts to import in Servlet

2. JSP Declaration:

<%! int i = 0; }  converts to class variable in Servlet

3. JSP Scriptlet:

<% int x = i %> converts into content of Servlet service method

4. JSP Expression

<%=x%> convert into servlet out.println("x")


## JSP Directive | Page | Include | Taglib

The jsp directives are messages that tells the web container how to translate a JSP page into the corresponding servlet.  
  
There are three types of directives:  
  
page directive  
include directive  
taglib directive  

###### @Page

The page directive defines attributes that apply to an entire JSP page.  

Syntax of JSP page directive  
	
	<%@ page attribute="value" %>  

Attributes of JSP page directive

* import
* contentType
* extends
* info
* buffer
* language
* isELIgnored
* isThreadSafe
* autoFlush
* session
* pageEncoding
* errorPage
* isErrorPage

Like above example it is use to import package  

	<%@ page import="java.util.Date" %>


###### @Include
Include any page(html or JSP) in JSP page

	<%@ include file="index.jsp" %> 


###### @Taglib

The JSP taglib directive is used to define a tag library that defines many tags.

	<%@ taglib uri="uriofthetaglibrary" prefix="prefixoftaglibrary" %>
	

## Implicit Object in JSP

There are 9 jsp implicit objects. These objects are created by the web container that are available to all the jsp pages.  

No. | Object | Type
---- | ----------- | -------------
1 | out | JspWriter
2 | request | HttpServletRequest
3 | response | HttpServletResponse
4 | config | ServletConfig
5 | application | ServletContext
6 | session | HttpSession
7 | pageContext | PageContext
8 | page | Object
9 | exception | Throwable


## Exception Handling in JSP

First method is to write code in Try Catch block But its not recommended  
Second we can create one error.jsp page and include it in jsp page  

	<%@ page errorPage="error.jsp" %>

And in error.jsp page include following code:

	<%@ page isErrorPage="true" %>  
  
	<h3>Sorry an exception occured!</h3>  

	Exception is: <%= exception %>  
	

## JST (JSP Standard Tag Library)

*From https://www.javatpoint.com/jstl

The JSP Standard Tag Library (JSTL) represents a set of tags to simplify the JSP development.

###### Advantage of JSTL
1. Fast Development JSTL provides many tags that simplify the JSP.
2. Code Reusability We can use the JSTL tags on various pages.
3. No need to use scriptlet tag It avoids the use of scriptlet tag.

*For creating JSTL application, you need to load the jstl.jar file.

There JSTL mainly provides five types of tags:
1. Core tags
2. Function tags
3. SQL tags
4. Formatting tags
5. XML tags

###### Sample Code

	<%@ page import="java.io.*,java.util.*,java.sql.*"%>  
	<%@ page import="javax.servlet.http.*,javax.servlet.*" %>  
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
	<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>  

	<html>  
	<head>  
	<title>sql:query Tag</title>  
	</head>  
	<body>  

	<sql:setDataSource var="db" driver="com.mysql.jdbc.Driver"  
	     url="jdbc:mysql://localhost/test"  
	     user="root"  password="1234"/>  

	<sql:query dataSource="${db}" var="rs">  
	SELECT * from Students;  
	</sql:query>  

	<table border="2" width="100%">  
	<tr>  
	<th>Student ID</th>  
	<th>First Name</th>  
	<th>Last Name</th>  
	<th>Age</th>  
	</tr>  
	<c:forEach var="table" items="${rs.rows}">  
	<tr>  
	<td><c:out value="${table.id}"/></td>  
	<td><c:out value="${table.First_Name}"/></td>  
	<td><c:out value="${table.Last_Name}"/></td>  
	<td><c:out value="${table.Age}"/></td>  
	</tr>  
	</c:forEach>  
	</table>  

	</body>  
	</html> 
	
	



	

















    
            
    

    
