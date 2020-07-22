package com.dileep;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SquareServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		int sum = 0;
		
		/*
		 * HttpSession session = req.getSession();
		 * 
		 * int sum = (int) session.getAttribute("sum");
		 */
		Cookie cookies [] = req.getCookies();
		
		for(Cookie cookie: cookies) {
			if(cookie.getName().equals("sum"))
				sum = Integer.parseInt(cookie.getValue());
		}
		
		int square = sum*sum;
		
		PrintWriter out = resp.getWriter();
		out.println("Square of sum of the numbers is: "+square);
		
		
	}

}
