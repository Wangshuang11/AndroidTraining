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
		
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-beijing.aliyuncs.com";
		
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = "LTAI4FoQ82rmSV5EzaE1KtPU";
		String accessKeySecret = "W8bsEiECRQfgYJJbD4rbPIdSWPaqTH";
		String bucketName = "jxy2019";
		String objectName = "avatars/"+System.currentTimeMillis()+".png";
		
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 创建PutObjectRequest对象。
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);

		// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
//		 ObjectMetadata metadata = new ObjectMetadata();
//		 metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//		 metadata.setObjectAcl(CannedAccessControlList.Private);
//		 putObjectRequest.setMetadata(metadata);

		// 上传文件。
		ossClient.putObject(putObjectRequest);

		// 关闭OSSClient。
		ossClient.shutdown();           
		in.close();
		
		//读取文件url
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
