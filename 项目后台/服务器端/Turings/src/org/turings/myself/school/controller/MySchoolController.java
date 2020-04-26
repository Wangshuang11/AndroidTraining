package org.turings.myself.school.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.entity.School;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.school.service.MySchoolService;

@Controller
//@RequestMapping("/lph")
public class MySchoolController {
	@Resource
	private MySchoolService mySchoolService;
	@ResponseBody
	@RequestMapping(value="/GetSchoolsList",produces="text/json;charset=utf-8")
	public String findSchool(@RequestParam(value="uid") String uid) {
		if(uid!=null) {
			int id = Integer.valueOf(uid);
			System.out.println("myself-school:查询用户id为："+id+"的学校列表");
			List<SchoolInfo> schools = this.mySchoolService.getSchoolsList(uid);
//			转成json串
			String jsonArray=this.mySchoolService.toJsonArray(schools);
			return jsonArray;
		}
		return null;
	}
}