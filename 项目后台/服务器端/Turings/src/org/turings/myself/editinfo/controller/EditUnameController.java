package org.turings.myself.editinfo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.myself.editinfo.service.EditUserInfoService;

/**
 * Servlet implementation class EditUname
 */
@WebServlet("/EditUname")
public class EditUnameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUnameController() {
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
		String uname =request.getParameter("uname");
		
		int uid = Integer.valueOf(userid);
		
		System.out.println(uid+" : "+uname);
//		int id =1; 
//		
		String result = new EditUserInfoService().editUserName(uid,uname);
//		String list = new FansService().getAttentionsList(id);
//		
		PrintWriter out = response.getWriter();  
		out.write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
