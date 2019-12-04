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
 * Servlet implementation class DeleteSubjectServlet
 */
@WebServlet("/DeleteSubjectServlet")
public class DeleteSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSubjectServlet() {
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
		//��ɾ��ǰ�Ȳ����һ������ߵ�һ��ͬ���͵���Ŀ
		SubjectMsg subjectMsg = new MistakenService().searchNextdeletedSubjectService(id, subject, tag, uId);
		//ɾ��ָ��id����Ŀ
		int n = new MistakenService().deleteSubjectService(id);
		PrintWriter out = response.getWriter();
		if(n>0) {//��ʾɾ���ɹ���������Ŀ
			if(subjectMsg == null) {//��ʾ�Ѿ�û��ͬ���͵���Ŀ�ˣ���ʾ���²���
				out.write("���һ���ⱻɾ�����أ�������ɸѡ��Ŀ��");
			}else {//������Ŀ
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				String msg = gson.toJson(subjectMsg);
				out.write(msg);
			}
		}else {//��ʾɾ��ʧ�ܣ���ʾ�û�����ɾ��
			out.write("ɾ��ʧ�ܣ�������ɾ��");
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
