package org.turings.index.story.controller;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.entity.Story;
import org.turings.index.story.service.StoryService;

@Controller
@RequestMapping("/lph")
public class StoryController {
	@Resource
	private StoryService storyService;
	@ResponseBody
	@RequestMapping(value="/findStoryAll",produces="text/json;charset=utf-8")
	public String findStoryAll() {
		List<Story>list=this.storyService.findStoryAll();
		System.out.println(this.storyService.toStringJson(list));
		return this.storyService.toStringJson(list);
	}
	@ResponseBody
	@RequestMapping("/updateStory")
	public String updateStory(@RequestParam(value="starnum") String starnum,
			@RequestParam(value="id")int id,@RequestParam(value="flag")int flag) {
		if(flag==1) {
			int n=this.storyService.updateStory(starnum,id);
			if(n>=1) {
				return "lph_one";
			}
			
		}else {
			int n=this.storyService.updateStory(starnum,id);
			if(n>=1) {
				return "two";
			}
		}
		return "null";
	}
}
