package org.turings.mistaken.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.mistaken.entity.SubjectMsg;
import org.turings.mistaken.service.MistakenService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class SearchNextSubjectServlet
 */
@WebServlet("/SearchNextSubjectServlet")
public class SearchNextSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchNextSubjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String subject = request.getParameter("subject");
		String tag = request.getParameter("tag");
		int uId = Integer.parseInt(request.getParameter("uId"));
		//在数据库中查询下一题
		SubjectMsg subjectMsgs = new MistakenService().searchNextSubjectService(id, subject, tag, uId);
		PrintWriter out = response.getWriter();
		if(subjectMsgs != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String subjectMsg = gson.toJson(subjectMsgs);
			out.write(subjectMsg);
		}else {
			out.write("这已经是最后一道题了");
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
