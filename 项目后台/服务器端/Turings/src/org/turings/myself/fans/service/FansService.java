package org.turings.myself.fans.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.turings.myself.entity.UserInfo;
import org.turings.myself.fans.dao.FansDao;
import org.turings.myself.fans.dao.SetAttentionDao;

import com.google.gson.Gson;

import net.sf.json.JSONArray;


/**
 * use：粉丝和被关注的人的 dao调用 �?  list转json
 * 
 * @method getFansList 查粉�?
 * @method getAttentionsList 查关�?
 * 
 * @author 大媛�?
 *
 */
public class FansService {
	
	/**
	 * use�? 根据id 获取 粉丝列表json
	 * @param id
	 * @return String
	 */
	public String getFansList(int id){
		
		String result  = null;
		
		//调用dao获取list
		List<UserInfo> list = new FansDao().getFansList(id);
		List<String> list0 = new ArrayList<>();
		
		//查到list之后转成json
		for (Iterator<UserInfo> iterator = list.iterator(); iterator.hasNext();) {
			UserInfo userInfo = (UserInfo) iterator.next();
			Gson gson = new Gson();
			String json = gson.toJson(userInfo);
			System.out.println(json);
//			JSONObject jsonObject = JSONObject.fromObject();
//			result = jsonObject.toString();
			list0.add(json);
			
		}
		JSONArray jArray = JSONArray.fromObject(list0);
//		
//		JSONObject jsonObject = JSONObject.fromObject(list);
		result = jArray.toString();
		
		System.out.println(result);
		return result;
		
	}
	
	
	/**
	 * use�? 根据id 获取 关注列表json
	 * @param id
	 * @return String
	 */
	public String getAttentionsList(int id){
		
		String result  = null;
		
		//调用dao获取list
		List<UserInfo> list = new FansDao().getAttentionList(id);
		List<String> list0 = new ArrayList<>();
		
		//查到list之后转成json
		for (Iterator<UserInfo> iterator = list.iterator(); iterator.hasNext();) {
			UserInfo userInfo = (UserInfo) iterator.next();
			Gson gson = new Gson();
			String json = gson.toJson(userInfo);
			System.out.println(json);
//			JSONObject jsonObject = JSONObject.fromObject();
//			result = jsonObject.toString();
			list0.add(json);
			
		}
		JSONArray jArray = JSONArray.fromObject(list0);
//		
//		JSONObject jsonObject = JSONObject.fromObject(list);
		result = jArray.toString();
		
		System.out.println(result);
		return result;
		
	}


	public String setAttention(int aid, int fid) {
		
		String result  = "false";
		
		//调用dao获取list

		result = new SetAttentionDao().setAttention(aid,fid);
		
		//查到list之后转成json
		
		return result;
	}

}