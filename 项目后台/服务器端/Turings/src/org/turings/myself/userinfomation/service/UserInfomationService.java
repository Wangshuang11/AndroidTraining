package org.turings.myself.userinfomation.service;

import org.turings.myself.entity.Myself;
import org.turings.myself.userinfomation.dao.UserInformationDao;
import com.google.gson.Gson;


/**
 * use：粉丝和被关注的人的 dao调用 和  list转json
 * 
 * @method getFansList 查粉丝
 * @method getAttentionsList 查关注
 * 
 * @author 大媛媛
 *
 */
public class UserInfomationService {
	
	/**
	 * use： 根据id 获取 用户自己的信息
	 * @param id
	 * @return String
	 */



	public String getUserInformation(int uid) {
		String result  = null;
		
		//调用dao获取imfomation
		Myself my = new UserInformationDao().getInfomationList(uid);
		
		
		//查到list之后转成json
		
			Gson gson = new Gson();
			String json = gson.toJson(my);
			System.out.println(json);
			
			
		result = json;
		
		System.out.println(result);
		return result;
	}

}