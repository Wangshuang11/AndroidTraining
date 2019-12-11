package org.turings.myself.inputavatar.service;

import org.turings.myself.inputavatar.dao.UpdateAvatarDao;

import net.sf.json.JSONObject;

public class UpdateAvatarService {

	public String getResule(int id, String url) {
		

		String res;
		String res0;
//		Map<String, String> map = new HashMap<String, String>();
		UpdateAvatarDao dao = new  UpdateAvatarDao();
		res = dao.uptoDate(id,url);
		res0 = dao.uptoAnotherDate(id, url);
		
		
//		将数据转改成json串
		
		// 创建JSONObject的实例
		JSONObject jsonObject = new JSONObject();
		// 调用put方法将user对象的数据
		// 采用key/value的形式放入JSONObject对象中
		jsonObject.put("result", res);
		jsonObject.put("result0", res0);
		jsonObject.put("url", url);
		
		String result ;
		result = jsonObject.toString();

		return result;
		
	
	}

}
