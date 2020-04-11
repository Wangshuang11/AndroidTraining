package org.turings.index.school.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.entity.School;
import org.turings.index.school.service.SchoolService;

@Controller
@RequestMapping("/lph")
public class SchoolController {
	@Resource
	private SchoolService schoolService;
	@ResponseBody
	@RequestMapping(value="/school",produces="text/json;charset=utf-8")
	public String findSchool(@RequestParam(value="flag") String flag) {
		if(flag!=null) {
			List<School>schools=this.schoolService.findSchoolByFlag(flag);
			String jsonArray=this.schoolService.toJsonArray(schools);
			return jsonArray;
		}
		return null;
	}
}
