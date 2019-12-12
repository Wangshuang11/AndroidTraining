package org.turings.myself.inputavatar.service;

import org.turings.myself.inputavatar.dao.UpdateAvatarDao;

import net.sf.json.JSONObject;

public class UpdateAvatarService {

	public String getResule(int id, String url) {
		

		String res;
//		String res0;
//		Map<String, String> map = new HashMap<String, String>();
		UpdateAvatarDao dao = new  UpdateAvatarDao();
		res = dao.uptoDate(id,url);
//		res0 = dao.uptoAnotherDate(id, url);
		
		
//		灏嗘暟鎹浆鏀规垚json涓�
		
		// 鍒涘缓JSONObject鐨勫疄渚�
		JSONObject jsonObject = new JSONObject();
		// 璋冪敤put鏂规硶灏唘ser瀵硅薄鐨勬暟鎹�
		// 閲囩敤key/value鐨勫舰寮忔斁鍏SONObject瀵硅薄涓�
		jsonObject.put("result", res);
//		jsonObject.put("result0", res0);
		jsonObject.put("url", url);
		
		String result ;
		result = jsonObject.toString();

		return result;
		
	
	}

}
