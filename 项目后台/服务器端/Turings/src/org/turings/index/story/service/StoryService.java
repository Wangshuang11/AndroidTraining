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
	public int updateStory(String starnum,int id) {
		return this.storyMapper.updateStory(starnum, id);
	}
	public String toStringJson(List<Story> list) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", list.get(i).getId());
			obj.put("title", list.get(i).getTitle());
			obj.put("smallimg", list.get(i).getSmallimg());
			obj.put("bigimg", list.get(i).getBigimg());
			obj.put("name", list.get(i).getName());
			obj.put("starnum", list.get(i).getStarnum());
			obj.put("content", list.get(i).getContent());
			array.add(obj);
		}
		JSONObject objt = new JSONObject();
		objt.put("list", array);
		return objt.toString();
	}
}
