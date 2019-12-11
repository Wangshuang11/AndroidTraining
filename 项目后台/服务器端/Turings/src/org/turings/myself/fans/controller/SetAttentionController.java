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
 * Servlet implementation class SetAttentionController
 */
@WebServlet("/SetAt")
public class SetAttentionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAttentionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @author 大媛媛
	 * @format "http://##/Turings/SetAt?aid=关注id&fid=用户id"  
	 * @use 通过用户id 返回所有关注的信息列表json
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		System.out.println("SetAttentionsListController请求");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String attentionId =request.getParameter("aid");
		String fansId =request.getParameter("fid");
		
		int aid = Integer.valueOf(attentionId);
		int fid = Integer.valueOf(fansId);
		System.out.println(aid+" : "+fid);
//		int id =1; 
//		
		String result = new FansService().setAttention(aid,fid);
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
