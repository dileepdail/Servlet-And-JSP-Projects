package com.dileep;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		int num1 = Integer.parseInt(req.getParameter("num1"));
		int num2 = Integer.parseInt(req.getParameter("num2"));
		
		int sum = num1 + num2;
		
		//PrintWriter out = resp.getWriter();
		//out.println("sum of the numbers is: "+sum);
		
		/*
		 * req.setAttribute("sum", sum);
		 * 
		 * RequestDispatcher rd = req.getRequestDispatcher("square"); rd.forward(req,
		 * resp);
		 */
		/*
		 * HttpSession session = req.getSession(); session.setAttribute("sum", sum);
		 */
		
		Cookie cookie = new Cookie("sum", ""+sum);
		resp.addCookie(cookie);
		
		resp.sendRedirect("square");
		
	}

}
