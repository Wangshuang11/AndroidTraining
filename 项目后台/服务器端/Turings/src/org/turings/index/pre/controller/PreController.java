package org.turings.index.pre.controller;

import java.util.List;

import org.turings.index.entity.Pre;
import org.turings.index.pre.dao.PreDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PreController {
	public List<Pre> findAll() {
		return new PreDao().findAll();
	}

	public String toJsonArray(List<Pre> list) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("title", list.get(i).getTitle());
			obj.put("img", list.get(i).getImg());
			obj.put("content", list.get(i).getContent());
			obj.put("src", list.get(i).getSrc());
			array.add(obj);
		}
		JSONObject objt = new JSONObject();
		objt.put("list", array);
		return objt.toString();
	}
}
