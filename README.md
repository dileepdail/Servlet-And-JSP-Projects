# Servlet-and-JSP

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

        <servlet>
            <servlet-name>addNumber</servlet-name>
            <servlet-class>com.dileep.AddServlet</servlet-class>
        </servlet>

        <servlet-mapping>
            <servlet-name>addNumber</servlet-name>
            <url-pattern>/add</url-pattern>
        </servlet-mapping>

3. Restart the server and test the html form with two numbers.

    
            
    

    
