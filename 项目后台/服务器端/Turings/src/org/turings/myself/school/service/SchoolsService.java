package org.turings.myself.school.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.school.dao.SchoolsDao;

import com.google.gson.Gson;

import net.sf.json.JSONArray;

public class SchoolsService {

	public String getSchoolsList(int id) {
		
		String result  = null;
		
		//调用dao获取list
		List<SchoolInfo> list = new SchoolsDao().getSchoolsList(id);
		List<String> list0 = new ArrayList<>();
		
		//查到list之后转成json
		for (Iterator<SchoolInfo> iterator = list.iterator(); iterator.hasNext();) {
			SchoolInfo schoolInfo = (SchoolInfo) iterator.next();
			Gson gson = new Gson();

			String json = gson.toJson(schoolInfo);
			System.out.println(json);
			
			list0.add(json);
			
		}
		
		JSONArray jArray = JSONArray.fromObject(list0);
		
		result = jArray.toString();
		
		System.out.println(result);
		return result;
		
	}

}
