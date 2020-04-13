package org.turings.myself.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.turings.myself.dao.MyselfMapper;
import net.sf.json.JSONObject;
@Service
public class UpdateAvatarService {
	@Autowired
	private MyselfMapper mapper;
	public String getResule(int id, String url) {
		String res;
		res = this.mapper.updateAvatar(id,url);
		// 创建JSONObject的实例
		JSONObject jsonObject = new JSONObject();
		// 调用put方法将user对象的数据
		jsonObject.put("result", res);
		jsonObject.put("url", url);
		String result ;
		result = jsonObject.toString();

		return result;
		
	
	}

}
