package org.turings.mistaken.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.mistaken.service.MistakenService;

/**
 * Servlet implementation class ChangeTagOfSubjectServlet
 */
@WebServlet("/ChangeTagOfSubjectServlet")
public class ChangeTagOfSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeTagOfSubjectServlet() {
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
		String tag = request.getParameter("tag");
		PrintWriter out = response.getWriter();
		//�����ݿ��и��ı�ǩ
		int n = new MistakenService().changeTagOfSubjectService(id, tag);
		if(n>0) {
			out.write("�޸ĳɹ�");
		}else {
			out.write("�޸�ʧ��");
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
