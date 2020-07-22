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
