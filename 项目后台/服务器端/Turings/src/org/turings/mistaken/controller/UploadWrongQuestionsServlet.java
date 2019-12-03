package org.turings.mistaken.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 * Servlet implementation class UploadWrongQuestionsServlet
 */
@WebServlet("/UploadWrongQuestionsServlet")
public class UploadWrongQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadWrongQuestionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		InputStream in = request.getInputStream();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String subjectJson = br.readLine();
		in.close();
		br.close();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		SubjectMsg subjectMsg = gson.fromJson(subjectJson, SubjectMsg.class);
		//向数据库中保存错题
		int n = new MistakenService().uploadWrongQuestionsService(subjectMsg);
		PrintWriter oPrintWriter = response.getWriter();
		if(n>0) {
			oPrintWriter.write("上传成功");
		}else {
			oPrintWriter.write("上传失败");
		}
		oPrintWriter.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
