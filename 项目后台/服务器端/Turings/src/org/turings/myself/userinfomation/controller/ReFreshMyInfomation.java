package org.turings.myself.userinfomation.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.turings.myself.userinfomation.service.UserInfomationService;

/**
 * Servlet implementation class ReFreshMyInfomation
 */
@WebServlet("/ReFreshMyInfomation")
public class ReFreshMyInfomation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReFreshMyInfomation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EditUname请求");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String userid =request.getParameter("uid");
		
		int uid = Integer.valueOf(userid);
		
		System.out.println("uid="+uid);

		
		String list = new UserInfomationService().getUserInformation(uid);
		
		PrintWriter out = response.getWriter();  
		out.write("["+list+"]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
