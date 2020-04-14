package org.turings.myself.myinformation.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.myself.entity.Myself;
import org.turings.myself.myinformation.service.MyInformationService;

@Controller
//@RequestMapping("/lph")
public class MyInformationController {
	@Resource
	private MyInformationService myInformationService;
	@ResponseBody
	@RequestMapping(value="/ReFreshMyInfomation",produces="text/json;charset=utf-8")
	public String findSchool(@RequestParam(value="uid") String uid) {
		if(uid!=null) {
			int id = Integer.valueOf(uid);
			System.out.println("myself-myInfo:查询用户id为："+id+"的个人信息");
			Myself myself = this.myInformationService.getMyInformation(uid);
//			转成json串
			System.out.println("controller"+myself.getId());
			String json = this.myInformationService.toJsonArray(myself);
			return json;
		}
		return null;
	}
}