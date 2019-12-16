package org.turings.near.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.near.entity.Information;
import org.turings.near.service.NearService;

import com.google.gson.Gson;

/**
 * Servlet implementation class BrowseInfoByServlet
 */
@WebServlet("/BrowseInfoByServlet")
public class BrowseInfoByServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseInfoByServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String name = request.getParameter("name");
		System.err.println(name+"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		
		NearService nearService = new NearService();
		Information info = nearService.browseInfoByName(name);
		
		Gson gson = new Gson();
		String json = gson.toJson(info);
		System.err.println(json);
		
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
