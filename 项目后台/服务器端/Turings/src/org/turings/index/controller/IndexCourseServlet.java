package org.turings.index.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.index.entity.Course;
import org.turings.index.service.IndexCourseService;


import com.google.gson.Gson;

/**
 * Servlet implementation class IndexCourseServlet
 */
@WebServlet("/IndexCourseServlet")
public class IndexCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Gson gson = new Gson();
		IndexCourseService service = new IndexCourseService();
		PrintWriter writer = response.getWriter();
		String key = request.getParameter("key");
		String parentString = request.getParameter("parentId");
		if (key!=null && key.equals("popular")) {
			
			try {
				List<Course> list = service.scanMostPopularCourse();
				String listsString = gson.toJson(list);
				writer.write(listsString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ( key != null && key.equals("change")) {
			try {
				List<Course> list = service.scanIndexCourse();
				String listsString = gson.toJson(list);
				writer.write(listsString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ( parentString != null) {
			try {
				List<Course> list = service.getChildCourses(parentString);
				String listsString = gson.toJson(list);
				writer.write(listsString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
