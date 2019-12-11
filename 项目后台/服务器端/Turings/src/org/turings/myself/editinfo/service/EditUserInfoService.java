package org.turings.myself.editinfo.service;

import org.turings.myself.editinfo.dao.EditUserInfoDao;

public class EditUserInfoService {

	public String editUserName(int uid, String uname) {
	
		// 调用dao 更改数据
		
		String result ;
		
		result = new EditUserInfoDao().editName(uid,uname);
		
		//TODO 将数据转改成json串
		
		
		
		return result;
	}

	public String editUserMotto(int uid, String umotto) {
		// 调用dao 更改数据
		
				String result ;
				
				result = new EditUserInfoDao().editMotto(uid,umotto);
				
				//TODO 将数据转改成json串
				
				
				
				return result;
	}

	
}
