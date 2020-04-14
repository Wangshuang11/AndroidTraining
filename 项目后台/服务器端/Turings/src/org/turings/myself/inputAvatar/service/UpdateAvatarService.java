package org.turings.myself.inputAvatar.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.myself.inputAvatar.dao.UpdateAvatarMapper;

import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false)
public class UpdateAvatarService {
	
	@Resource
	private UpdateAvatarMapper updateAvatarMapper;


	public String getResule(int id, String url) {
		
		
//		System.out.println("哪个是null"+updateAvatarMapper);

		
//		this.dao.uptoDate(0,"0");
		
		
		System.out.println("哪个是null"+updateAvatarMapper);
		this.updateAvatarMapper.uptoDate(id,url);
		
//		将数据转改成json串
		// 创建JSONObject的实例
		JSONObject jsonObject = new JSONObject();
		// 调用put方法将user对象的数据
		// 采用key/value的形式放入JSONObject对象中
		jsonObject.put("result", "true");
		jsonObject.put("url", url);
		
		String result = jsonObject.toString();
		return result;
	}
	
}
