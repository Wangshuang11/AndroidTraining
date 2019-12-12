package org.turings.myself.inputavatar.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.turings.myself.inputavatar.service.UpdateAvatarService;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * Servlet implementation class InputAvatarController
 */
@WebServlet("/InputAvatar")
public class InputAvatarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InputAvatarController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("UpFileServlet");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//kk
		
		String AttentionId =request.getParameter("id");
		int id = Integer.valueOf(AttentionId);
		
		
		
		
		
//################################################################################################
		InputStream in = request.getInputStream();
		
		// Endpoint浠ユ澀宸炰负渚嬶紝鍏跺畠Region璇锋寜瀹為檯鎯呭喌濉啓銆�
		String endpoint = "http://oss-cn-beijing.aliyuncs.com";
		
		// 闃块噷浜戜富璐﹀彿AccessKey鎷ユ湁鎵�鏈堿PI鐨勮闂潈闄愶紝椋庨櫓寰堥珮銆傚己鐑堝缓璁偍鍒涘缓骞朵娇鐢≧AM璐﹀彿杩涜API璁块棶鎴栨棩甯歌繍缁达紝璇风櫥褰� https://ram.console.aliyun.com 鍒涘缓RAM璐﹀彿銆�
		String accessKeyId = "LTAI4FoQ82rmSV5EzaE1KtPU";
		String accessKeySecret = "W8bsEiECRQfgYJJbD4rbPIdSWPaqTH";
		String bucketName = "jxy2019";
		String objectName = "avatars/i"+id+"t"+System.currentTimeMillis()+".png";
		
		// 鍒涘缓OSSClient瀹炰緥銆�
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 鍒涘缓PutObjectRequest瀵硅薄銆�
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);

		// 濡傛灉闇�瑕佷笂浼犳椂璁剧疆瀛樺偍绫诲瀷涓庤闂潈闄愶紝璇峰弬鑰冧互涓嬬ず渚嬩唬鐮併��
//		 ObjectMetadata metadata = new ObjectMetadata();
//		 metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//		 metadata.setObjectAcl(CannedAccessControlList.Private);
//		 putObjectRequest.setMetadata(metadata);

		// 涓婁紶鏂囦欢銆�
		ossClient.putObject(putObjectRequest);

		// 鍏抽棴OSSClient銆�
		ossClient.shutdown();           
		in.close();
		
		//璇诲彇鏂囦欢url
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
		System.out.println(url);		
//################################################################################################	
		
	
		UpdateAvatarService updateAvatarService = new UpdateAvatarService();
		String result = updateAvatarService.getResule(id,url);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
