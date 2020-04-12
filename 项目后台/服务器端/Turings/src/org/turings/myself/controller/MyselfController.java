package org.turings.myself.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.login.entity.SendSms;
import org.turings.myself.entity.CourseInfo;
import org.turings.myself.service.MyselfService;

@Controller
public class MyselfController {
	@Resource
	private MyselfService myselfService;
	@ResponseBody
	//这个mappingvalue的内容目前是瞎写的
	@RequestMapping(value="/SendCourse",produces="text/json;charset=utf-8")
	public List<CourseInfo> sendCourse() {
		return this.myselfService.listAllCourses();
	}
}
