package org.turings.myself.course.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.turings.myself.course.dao.CoursesDao;
import org.turings.myself.entity.CourseInfo;

import com.google.gson.Gson;

import net.sf.json.JSONArray;

public class CoursesService {

	public String getCoursesList(int id) {
		
		String result  = null;
		
		//调用dao获取list
		List<CourseInfo> list = new CoursesDao().getCoursesList(id);
		List<String> list0 = new ArrayList<>();
		
		//查到list之后转成json
		for (Iterator<CourseInfo> iterator = list.iterator(); iterator.hasNext();) {
			CourseInfo CourseInfo = (CourseInfo) iterator.next();
			Gson gson = new Gson();

			String json = gson.toJson(CourseInfo);
			System.out.println(json);
			
			list0.add(json);
			
		}
		
		JSONArray jArray = JSONArray.fromObject(list0);
		
		result = jArray.toString();
		
		System.out.println(result);
		return result;
		
	}

}
