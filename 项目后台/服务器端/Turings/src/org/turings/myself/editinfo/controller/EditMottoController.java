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
 * Servlet implementation class EditMottoController
 */
@WebServlet("/EditMotto")
public class EditMottoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMottoController() {
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
		String umotto =request.getParameter("umotto");
		
		int uid = Integer.valueOf(userid);
		
		System.out.println(uid+" : "+umotto);
//		int id =1; 
//		
		String result = new EditUserInfoService().editUserMotto(uid,umotto);
//		String list = new FansService().getAttentionsList(id);
//		
		PrintWriter out = response.getWriter();  
		out.write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
