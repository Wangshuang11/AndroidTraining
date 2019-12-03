package org.turings.index.school.controller;

import java.util.List;

import org.turings.index.entity.School;
import org.turings.index.school.dao.SchoolDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SchoolController {
	public List<School> findSchool(String flag) {
		return new SchoolDao().findSchool(flag);
	}

	public String toJsonArray(List<School> list) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("name", list.get(i).getName());
			obj.put("img", list.get(i).getImg());
			obj.put("url", list.get(i).getUrl());
			obj.put("src", list.get(i).getSrc());
			array.add(obj);
		}
		JSONObject objt = new JSONObject();
		objt.put("list", array);
		return objt.toString();
	}

}
