package org.turings.myself.fans.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.myself.fans.service.FansService;

/**
 * Servlet implementation class AttentionsListController
 */
@WebServlet("/AtList")
public class AttentionsListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttentionsListController() {
        super();
    }
	/**
	 * @author 大媛�?
	 * @format "http://##/Turings/AtList?uid=用户id"  
	 * @use 通过用户id 返回�?有关注的信息列表json
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AttentionsListController请求");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String AttentionId =request.getParameter("fid");
		int id = Integer.valueOf(AttentionId);
		System.out.println(id);
//		int id =1; 
		
		String list = new FansService().getAttentionsList(id);
		
		PrintWriter out = response.getWriter();  
		out.write(list);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
