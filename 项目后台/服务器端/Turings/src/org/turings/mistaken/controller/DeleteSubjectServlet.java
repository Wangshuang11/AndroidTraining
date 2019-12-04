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
		//在删除前先查出下一道题或者第一条同类型的题目
		SubjectMsg subjectMsg = new MistakenService().searchNextdeletedSubjectService(id, subject, tag, uId);
		//删除指定id的题目
		int n = new MistakenService().deleteSubjectService(id);
		PrintWriter out = response.getWriter();
		if(n>0) {//表示删除成功，传回题目
			if(subjectMsg == null) {//表示已经没有同类型的题目了，提示重新查找
				out.write("最后一道题被删除了呢，请重新筛选题目吧");
			}else {//返回题目
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				String msg = gson.toJson(subjectMsg);
				out.write(msg);
			}
		}else {//表示删除失败，提示用户重新删除
			out.write("删除失败，请重新删除");
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
