package org.turings.myself.farm.task.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.myself.farm.entity.MyTask;
import org.turings.myself.farm.task.service.TaskService;

@Controller
//@RequestMapping("/jes")
public class TaskController {
	@Resource
	private TaskService taskService;
	@ResponseBody
	@RequestMapping(value="/getMyTasks",produces="text/json;charset=utf-8")
	public String getMyTasks(@RequestParam(value="uid") String uid) {
		if(uid!=null) {
			int id = Integer.valueOf(uid);
			System.out.println("getMyTasks");
			
			MyTask myTask = this.taskService.getMyTasks(id);
			this.taskService.changeLoginStatusTo1(id);
			String json=this.taskService.toJson(myTask);
			return json;
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value="/changeLoginTaskWater",produces="text/json;charset=utf-8")
	public String changeLoginTaskWater(@RequestParam(value="uid") String uid) {
		if(uid!=null) {
			int id = Integer.valueOf(uid);
			System.out.println("changeLoginTaskWater");
			
			this.taskService.changeWater(id,5);
			this.taskService.changeLoginStatusTo2(id);
			
			return "true";
		}
		return "false";
	}
	
}