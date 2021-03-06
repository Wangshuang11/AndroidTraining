package org.turings.index.school.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.index.entity.School;
import org.turings.index.school.dao.SchoolMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=true)
public class SchoolService {
	@Resource
	private SchoolMapper schoolMapper;
	public List<School> findSchoolByFlag(String flag){
		return this.schoolMapper.findSchoolByFlag(flag);
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
