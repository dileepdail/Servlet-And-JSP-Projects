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
6. Enter some text in the file like "Hello World"
7. Run the project as a server.

## Create Servlet & Web.xml
1. Create Servlet
    Right click pn project. Select Class. Enter class name  
    Add "extends HttpServlet" to make your class servlet class  
    Add and implement service method to your servlet class with request and response object HttpServletRequest req, HttpServletResponse resp
            
    

    
