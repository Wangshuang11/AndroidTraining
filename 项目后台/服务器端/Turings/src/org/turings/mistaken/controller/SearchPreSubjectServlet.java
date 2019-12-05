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
 * Servlet implementation class SearchPreSubjectServlet
 */
@WebServlet("/SearchPreSubjectServlet")
public class SearchPreSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchPreSubjectServlet() {
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
		//�����ݿ��в�ѯ��һ��
		SubjectMsg subjectMsgs = new MistakenService().searchPreSubjectService(id, subject, tag, uId);
		PrintWriter out = response.getWriter();
		if(subjectMsgs != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String subjectMsg = gson.toJson(subjectMsgs);
			System.out.print(subjectMsg);
			out.write(subjectMsg);
		}else {
			out.write("���Ѿ��ǵ�һ����");
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
