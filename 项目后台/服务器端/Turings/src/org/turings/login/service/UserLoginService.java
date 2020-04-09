package org.turings.login.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.login.dao.UserLoginMapper;
import org.turings.login.entity.User;

@Service
@Transactional(readOnly=true)
public class UserLoginService {
	@Resource
	private UserLoginMapper userLoginMapper;

	public String loginCheckByName(User user) {
		String result="false";
		User user2=userLoginMapper.queryUser(user);
		if(user2!=null) {
			result=user2.getuId()+"";
		}
		return result;
	}

	public String loginCheckByPhone(User user) {
		return loginCheckByName(user);
	}

	public String loginCheckByUserPwd(User user) {
		return loginCheckByName(user);
	}

	@Transactional(readOnly=false)
	public boolean modifyPwdByPhone(User user) {
		boolean flag=false;
		if(userLoginMapper.modifyUser(user)!=0) {
			flag=true;
		}
		return flag;
	}

	@Transactional(readOnly=false)
	public boolean addUser(User user) {
		String uAvatar="https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/i1t1576111585139.png";//Ĭ��ͷ��
		String uMotto="�����Ҳű�����";//Ĭ��������
		user.setuAvatar(uAvatar);
		user.setuMotto(uMotto);
		//������tbl_self_user������û����id
        userLoginMapper.insertUser(user);
		if(user.getuId()>=0) {
			//��tbl_position��������һ���û�:ͷ������Ĭ��
			if(userLoginMapper.insertUserPosition(user.getuName(),"i21","37.9977","114.52262")!=0) {
				//��tbl_self_schools_favorite��������һ���û���Ĭ��Ŀ���ѧ���廪��ѧ
				if (userLoginMapper.insertUserSchool(1,user.getuId())!=0) {
					return true;
				}
			}
		}
		return false;
	}

}
