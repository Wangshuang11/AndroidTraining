package org.turings.myself.farm.task.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.farm.entity.MyTask;
import org.turings.myself.farm.task.dao.TaskMapper;

import com.google.gson.Gson;

import net.sf.json.JSONArray;

@Service
@Transactional(readOnly=true)
public class TaskService {
	@Resource
	private TaskMapper taskMapper;
	
	
	public MyTask getMyTasks(int id) {
		
		return this.taskMapper.getMyTasks(id);
	}
	
	@Transactional(readOnly=false)
	public void refreshTaskTable() {
		this.taskMapper.refreshLoginTask();
	}
	
	@Transactional(readOnly=false)
	public void changeWater(int id, int num) {
		this.taskMapper.changeWater(id,num);
	}
	@Transactional(readOnly=false)
	public void changeLoginStatusTo1(int id) {
		this.taskMapper.changeLoginStatus(id,1);
	}
	@Transactional(readOnly=false)
	public void changeLoginStatusTo2(int id) {
		this.taskMapper.changeLoginStatus(id,2);
		
	}

	
	public String toJson(Object a) {
		Gson gson = new Gson();
		String json = gson.toJson(a);
		return json.toString();
	}
	
	public <T> String toJsonArray(List<T> list) {
		
		
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
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T a = (T) iterator.next();
			Gson gson = new Gson();
			String json = gson.toJson(a);
//			System.out.println(json);			
			list0.add(json);
		}		
		JSONArray jArray = JSONArray.fromObject(list0);
		return jArray.toString();
	}

	

}
