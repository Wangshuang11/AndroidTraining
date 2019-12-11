package org.turings.near.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.near.entity.Share;
import org.turings.near.service.NearService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class BrowseShareServlet
 */
@WebServlet("/BrowseShareServlet")
public class BrowseShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseShareServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userName = request.getParameter("userName");
		System.out.println(userName+"ÓÐÅ¶ÓÆÓÆ");
		
		NearService nearService = new NearService();
		List<Share> shareList = nearService.browseShare(userName);
		
		String json = JSONArray.fromObject(shareList).toString();
		
		System.out.println(json+"browseShare");
		
		PrintWriter writer = response.getWriter();
		writer.write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
