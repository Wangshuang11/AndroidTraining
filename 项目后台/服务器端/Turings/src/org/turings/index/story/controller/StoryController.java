package org.turings.index.story.controller;

import java.util.List;

import org.turings.index.entity.Story;
import org.turings.index.story.dao.StoryDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StoryController {
	public List<Story> findStoryAll() {
		return new StoryDao().findStoryAll();
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

	public String updateStory(String title, String flag) {
		return new StoryDao().updateStory(title, flag);
	}
}
