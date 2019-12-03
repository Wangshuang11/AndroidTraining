package org.turings.mistaken.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 * Servlet implementation class SearchSubjectMsgBySubjectServlet
 */
@WebServlet("/SearchSubjectMsgBySubjectServlet")
public class SearchSubjectMsgBySubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchSubjectMsgBySubjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String subject = request.getParameter("subject");
		String tag = request.getParameter("tag");
		System.out.println(tag);
		int uId = Integer.parseInt(request.getParameter("uId"));
		List<SubjectMsg> subjectMsgs = new MistakenService().searchSubjectMsgBySubjectAndTagService(subject, uId,tag);
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String subjectMsgList = gson.toJson(subjectMsgs);
		System.out.print(subjectMsgList);
		out.write(subjectMsgList);
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
