package org.turings.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.login.entity.User;
import org.turings.login.service.UserService;

/**
 * Servlet implementation class UserModifyPwdByPhone
 */
@WebServlet("/UserModifyPwdByPhone")
public class UserModifyPwdByPhone extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserModifyPwdByPhone() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String uTel = request.getParameter("uTel");
		String uPwd = request.getParameter("uPwd");
		System.out.println("ws");
		System.out.println("wsTel"+uTel);
		System.out.println("wsPwd"+uPwd);
		User user=new User();
		user.setuTel(uTel);
		user.setuPwd(uPwd);
		writer.write(new UserService().modifyPwdByPhone(user)+"");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
