package org.turings.myself.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.login.entity.SendSms;
import org.turings.myself.entity.CourseInfo;
import org.turings.myself.entity.Myself;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.entity.UserInfo;
import org.turings.myself.service.MyselfService;
import org.turings.myself.service.UpdateAvatarService;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

import net.sf.json.JSONArray;

@Controller
public class MyselfController {
	@Resource
	private MyselfService myselfService;
	@ResponseBody
	//显示全部课程
	@RequestMapping(value="/GetCoursesList",produces="text/json;charset=utf-8")
	public  String sendCourse(@RequestParam(value = "uid") int uid) {
		List<CourseInfo> courseInfos= this.myselfService.listAllCourses(uid);
		String json = JSONArray.fromObject(courseInfos).toString();
		return json;
	}
	//显示全部粉丝
	@ResponseBody
	@RequestMapping(value="/FansList",produces="text/json;charset=utf-8")
	public String listFan(@RequestParam(value = "uid") int uid) {
		List<UserInfo> userInfos= this.myselfService.listAllFans(uid);
		String json = JSONArray.fromObject(userInfos).toString();
		return json;
	}
	//显示全部关注
	@ResponseBody
	@RequestMapping(value="/AtList",produces="text/json;charset=utf-8")
	public String listAttention(@RequestParam(value = "fid") int fid) {
		List<UserInfo> userInfos= this.myselfService.listAllAttentions(fid);
		String json = JSONArray.fromObject(userInfos).toString();
		return json;
	}
	//添加关注
	@ResponseBody
	@RequestMapping(value="/SetAt",produces="text/json;charset=utf-8")
	public String addAttention(@RequestParam(value = "aid") int attentionId,@RequestParam(value = "fid") int fanId) {
		int result= this.myselfService.addAttentions(attentionId,fanId);
		if(result==1) {
			return "true";
		}else {
			return "false";
		}
	}
	//取消关注
	@ResponseBody
	@RequestMapping(value="/DelAt",produces="text/json;charset=utf-8")
	public String delAttention(@RequestParam(value = "aid") int attentionId,@RequestParam(value = "fid") int fanId) {
		int result= this.myselfService.delAttentions(attentionId,fanId);
		if(result==1) {
			return "true";
		}else {
			return "false";
		}
	}
	//编辑座右铭
	@ResponseBody
	@RequestMapping(value="/EditMotto",produces="text/json;charset=utf-8")
	public String editMotto(@RequestParam(value = "uid") int uid,@RequestParam(value = "umotto") int uMotto) {
		int result= this.myselfService.editMotto(uid,uMotto);
		if(result==1) {
			return "true";
		}else {
			return "false";
		}
	}
	//编辑网名
	@ResponseBody
	@RequestMapping(value="/EditUname",produces="text/json;charset=utf-8")
	public String edituName(@RequestParam(value = "uid") int uid,@RequestParam(value = "uname") int uName) {
		int result= this.myselfService.edituName(uid,uName);
		if(result==1) {
			return "true";
		}else {
			return "false";
		}
	}
	/*//修改头像
	@ResponseBody
	@RequestMapping(value="/InputAvatar",produces="text/json;charset=utf-8")
	public String editAvatar(@RequestParam(value = "id") int uid) {
		
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-beijing.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = "LTAI4FoQ82rmSV5EzaE1KtPU";
		String accessKeySecret = "W8bsEiECRQfgYJJbD4rbPIdSWPaqTH";
		String bucketName = "jxy2019";
		String objectName = "avatars/i"+uid+"t"+System.currentTimeMillis()+".png";
		String url = "http://jxy2019.oss-cn-beijing.aliyuncs.com/"+objectName;
		URL url1;
		try {
			url1 = new URL(url);
			InputStream in =url1.openStream();
			// 创建OSSClient实例。
			OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
			// 创建PutObjectRequest对象。
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);
			// 上传文件。
			ossClient.putObject(putObjectRequest);
			// 关闭OSSClient。
			ossClient.shutdown();           
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		UpdateAvatarService updateAvatarService = new UpdateAvatarService();
		String result = updateAvatarService.getResule(uid,url);
		return result;
	}
	//显示学校
	@ResponseBody
	@RequestMapping(value="/GetSchoolsList",produces="text/json;charset=utf-8")
	public List<SchoolInfo> showSchool(@RequestParam(value = "uid") int uid) {
		int a=this.myselfService.listSchools(1).size();
		return this.myselfService.listSchools(1);
	}
	//显示用户信息
	@ResponseBody
	@RequestMapping(value="/ReFreshMyInfomation",produces="text/json;charset=utf-8")
	public List<Myself> UrefreshUserInformation(@RequestParam(value = "uid") int uid) {
		return this.myselfService.refreshUserInfo(uid);
	}*/
}

