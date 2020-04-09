package org.turings.index.pre.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.entity.Pre;
import org.turings.index.pre.service.PreService;

@Controller
@RequestMapping("/lph")
public class PreController {
	@Resource
	private PreService preService;
	@ResponseBody
	@RequestMapping(value="/findPreAll",produces="text/json;charset=utf-8")
	public String findPreAll() {
		List<Pre>pres=this.preService.findPreAll();
		String string=this.preService.toJsonArray(pres);
		System.out.println(string);
		return string;
	}
}
