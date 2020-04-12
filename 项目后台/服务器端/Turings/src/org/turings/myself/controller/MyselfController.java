package org.turings.myself.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.entity.School;
import org.turings.login.entity.SendSms;
import org.turings.myself.entity.CourseInfo;
import org.turings.myself.entity.Myself;
import org.turings.myself.entity.UserInfo;
import org.turings.myself.service.MyselfService;

@Controller
public class MyselfController {
	@Resource
	private MyselfService myselfService;
	@ResponseBody
	//显示全部课程
	@RequestMapping(value="/GetCoursesList",produces="text/json;charset=utf-8")
	public List<CourseInfo> sendCourse(@RequestParam(value = "uid") int uid) {
		return this.myselfService.listAllCourses(uid);
	}
	//显示全部粉丝
	@RequestMapping(value="/FansList",produces="text/json;charset=utf-8")
	public List<UserInfo> listFan(@RequestParam(value = "uid") int uid) {
		return this.myselfService.listAllFans(uid);
	}
	//显示全部关注
	@RequestMapping(value="/AtList",produces="text/json;charset=utf-8")
	public List<UserInfo> listAttention(@RequestParam(value = "fid") int fid) {
		return this.myselfService.listAllAttentions(fid);
	}
	//添加关注
	@RequestMapping(value="/SetAt",produces="text/json;charset=utf-8")
	public int addAttention(@RequestParam(value = "aid") int attentionId,@RequestParam(value = "fid") int fanId) {
		return this.myselfService.addAttentions(attentionId,fanId);
	}
	//编辑座右铭
	@RequestMapping(value="/EditMotto",produces="text/json;charset=utf-8")
	public int editMotto(@RequestParam(value = "uid") int uid,@RequestParam(value = "umotto") int uMotto) {
		return this.myselfService.editMotto(uid,uMotto);
	}
	//编辑网名
	@RequestMapping(value="/EditUname",produces="text/json;charset=utf-8")
	public int edituName(@RequestParam(value = "uid") int uid,@RequestParam(value = "uname") int uName) {
		return this.myselfService.edituName(uid,uName);
	}
	//修改头像
	@RequestMapping(value="/InputAvatar",produces="text/json;charset=utf-8")
	public int editAvatar(@RequestParam(value = "uid") int uid,@RequestParam(value = "uname") int uName) {
		return this.myselfService.edituName(uid,uName);
	}
	//显示学校
	@RequestMapping(value="/InputAvatar",produces="text/json;charset=utf-8")
	public List<School> showSchool(@RequestParam(value = "uid") int uid) {
		return this.myselfService.listSchools(uid);
	}
	//显示用户信息
	@RequestMapping(value="/ReFreshMyInfomation",produces="text/json;charset=utf-8")
	public List<Myself> UrefreshUserInformation(@RequestParam(value = "uid") int uid) {
		return this.myselfService.refreshUserInfo(uid);
	}
}

