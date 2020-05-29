package org.turings.myself.farm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Tast {
	@ResponseBody
	@RequestMapping(value="/yyyyuuu",produces="text/json;charset=utf-8")
	public String findSchool(@RequestParam(value="uid") String uid) {
		
			int id = Integer.valueOf(uid);
			System.out.println("getMyTasks");
			
			String json="json";
			return json;
	}
}
