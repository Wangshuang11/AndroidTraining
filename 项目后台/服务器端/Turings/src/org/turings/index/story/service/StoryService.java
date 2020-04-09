package org.turings.index.story.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.index.entity.Story;
import org.turings.index.story.dao.StoryMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false)
public class StoryService {
	@Resource
	private StoryMapper storyMapper;
	@Transactional(readOnly=true)
	public List<Story> findStoryAll(){
		return this.storyMapper.findStoryAll();
	}
	public String toStringJson(List<Story> list) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("title", list.get(i).getTitle());
			obj.put("img1", list.get(i).getImg1());
			obj.put("img2", list.get(i).getImg2());
			obj.put("img3", list.get(i).getImg3());
			obj.put("num", list.get(i).getNum());
			obj.put("content", list.get(i).getContent());
			array.add(obj);
		}
		JSONObject objt = new JSONObject();
		objt.put("list", array);
		return objt.toString();
	}
}
