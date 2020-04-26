package org.turings.index.HotVideo.controller;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.HotVideo.service.HotVideoService;
import org.turings.index.entity.HotCourse;
import org.turings.index.entity.HotVideo;
import org.turings.index.entity.Story;

import com.google.gson.Gson;

@Controller
@RequestMapping("/gw")
public class HotVideoController {
	@Resource
	private HotVideoService service;
	@ResponseBody
	@RequestMapping(value="/findVideoAll",produces="text/json;charset=utf-8")
	public String findVideoAll() {
		
		List<HotVideo> list=this.service.findVideoAll();
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	
	@ResponseBody
	@RequestMapping(value="/findVideoMore",produces="text/json;charset=utf-8")
	public String findVideoMore(@RequestParam(value = "begin") int begin) {
		
		List<HotVideo> list=this.service.findVideoMore(begin);
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/SearchVideo",produces="text/json;charset=utf-8")
	public String SearchVideo(@RequestParam(value="search") String  search) {
		System.out.println(search);
		List<HotVideo> list=this.service.SearchVideo(search);
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	@ResponseBody
	@RequestMapping(value="/indexcourse",produces="text/json;charset=utf-8")
	public String findIndexCourse() {
		
		List<HotCourse> list=this.service.findIndexCourse();
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	@ResponseBody
	@RequestMapping(value="/changeCourse",produces="text/json;charset=utf-8")
	public String changeCourse() {
		int a =(int) Math.floor(Math.random()*10+1);
		List<HotCourse> list=this.service.changeCourse(a,2);
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	
	@ResponseBody
	@RequestMapping(value="/Detail",produces="text/json;charset=utf-8")
	public String Detail(@RequestParam(value="parentId") String  parentId) {
		List<HotCourse> list=this.service.Detail(parentId);
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	
}
