package org.turings.myself.inputAvatar.controller;


import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.myself.inputAvatar.service.UpdateAvatarService;
import org.turings.myself.school.service.MySchoolService;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;


@Controller
public class InputAvatarController {

//	使用httpRequest
	@Autowired HttpServletRequest request;
	
	@Resource
	private UpdateAvatarService updateAvatarService;
	@ResponseBody
	@RequestMapping(value="/InputAvatar",produces="text/json;charset=utf-8")
	public String findSchool(@RequestParam(value="id") String uid) {
		System.out.println("UpFileServlet");
	
		
		int id = Integer.valueOf(uid);
		
//################################################################################################
		InputStream in;
		String objectName="";
		try {
			in = request.getInputStream();
		
		
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-beijing.aliyuncs.com";
		
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = "";
		String accessKeySecret = "";
		String bucketName = "jxy2019";
		objectName = "avatars/i"+id+"t"+System.currentTimeMillis()+".png";
		
		
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
		} catch (IOException e) {
			System.err.println("self-InputAvatarController:存图片到oss失败");
			e.printStackTrace();
		}
		
		
		//读取文件url
//		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
//		String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
//		System.out.println(url);		
//################################################################################################	
		
		String url = "http://jxy2019.oss-cn-beijing.aliyuncs.com/"+objectName;
	
//		UpdateAvatarService updateAvatarService = new UpdateAvatarService();
		
		System.out.println("用户id ："+id + "头像路径："+url);
		String result = updateAvatarService.getResule(id,url);
				
		return result;
	}
}
