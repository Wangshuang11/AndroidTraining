package org.turings.myself.school.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.school.dao.MySchoolMapper;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=true)
public class MySchoolService {
	
	
	@Resource
	private MySchoolMapper mySchoolMapper;
	
	
//	public List<SchoolInfo> findSchoolByFlag(String flag){
//		return this.mySchoolMapper.findSchoolByFlag(flag);
//	}
	
	public List<SchoolInfo> getSchoolsList(String uid) {
		
		System.out.println("这个是null吗？"+mySchoolMapper);
		return this.mySchoolMapper.getSchoolsList(uid);
	}
	
	public String toJsonArray(List<SchoolInfo> schools) {
		
		
//		遍历list值得学习的get(i)
//		JSONArray array = new JSONArray();
//		for (int i = 0; i < schools.size(); i++) {
//			JSONObject obj = new JSONObject();
//			obj.put("id", schools.get(i).getId());
//			obj.put("name", schools.get(i).getName());
//			obj.put("img", schools.get(i).getImg());
//			obj.put("url", schools.get(i).getUrl());
//			obj.put("src", schools.get(i).getSrc());
//			obj.put("flag", schools.get(i).getFlag());
//			array.add(obj);
//		}
//		JSONObject objt = new JSONObject();
//		objt.put("list", array);
//		return objt.toString();
		
		
		//查到list之后转成json
		List<String> list0 = new ArrayList<>();
		for (Iterator<SchoolInfo> iterator = schools.iterator(); iterator.hasNext();) {
			SchoolInfo schoolInfo = (SchoolInfo) iterator.next();
			Gson gson = new Gson();
			String json = gson.toJson(schoolInfo);
//			System.out.println(json);			
			list0.add(json);
		}		
		JSONArray jArray = JSONArray.fromObject(list0);
		return jArray.toString();
	}


	
}
