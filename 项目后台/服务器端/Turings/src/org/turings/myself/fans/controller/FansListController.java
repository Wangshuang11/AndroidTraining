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
 * editor=金鑫媛
 * Servlet implementation class FansServlet
 */
@WebServlet("/FansList")
public class FansListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FansListController() {
        super();
    }

	/**
	 * @author 大媛媛
	 * @format "http://##/Turings/FansList?uid=用户id"  
	 * @use 通过用户id 返回所有粉丝的信息列表json
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("controller");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String AttentionId =request.getParameter("uid");
		int id = Integer.valueOf(AttentionId);
		System.out.println(id);
//		int id =1; 
		
		String list = new FansService().getFansList(id);
		
		PrintWriter out = response.getWriter();  
		out.write(list);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
