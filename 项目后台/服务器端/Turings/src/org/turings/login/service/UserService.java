package org.turings.login.service;

import org.turings.login.dao.UserDao;
import org.turings.login.entity.User;

public class UserService {

	public UserService() {
		// TODO Auto-generated constructor stub
	}

	public String loginCheckByUserPwd(User user) {
		String uName=user.getuName();
		String uPwd=user.getuPwd();
		String sql="select * from tbl_self_user where uName='"+uName+"' and uPwd='"+uPwd+"'";
		return new UserDao().queryUser(sql);
	}
	
	public String loginCheckByPhone(User user) {
		String uTel=user.getuTel();
		String sql="select * from tbl_self_user where uTel='"+uTel+"'";
		return new UserDao().queryUser(sql);
	}

	public boolean modifyPwdByPhone(User user) {
		String uTel=user.getuTel();
		String uPwd=user.getuPwd();
		String sql="update tbl_self_user set uPwd='"+uPwd+"' where uTel='"+uTel+"'";
		return new UserDao().modifyUser(sql);
	}

	public String loginCheckByName(User user) {
		String uName=user.getuName();
		String sql="select * from tbl_self_user where uName='"+uName+"'";
		return new UserDao().queryUser(sql);
	}

	public boolean addUser(User user) {
		String uName=user.getuName();
		String uPwd=user.getuPwd();
		String uTel=user.getuTel();
		String uAvatar="https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/i1t1576111585139.png";//Ĭ��ͷ��
		String uMotto="�����Ҳű�����";//Ĭ��������
		String sql="insert into tbl_self_user(uTel,uName,uPwd,uMotto,uAvatar) values('"+uTel+"','"+uName+"','"+uPwd+"','"+uMotto+"','"+uAvatar+"')";
		
		if(new UserDao().modifyUser(sql)) {/*��user��������һ���û�*/
			/*��ѯ�������û���uId*/
			int uId=Integer.parseInt(new UserDao().queryUser("select * from tbl_self_user where uName='"+uName+"'"));
			String sqlPosition="insert into tbl_position(id,username,portrait,lat,lng) values("+uId+",'"+uName+"','i21','37.9977','114.52262')";
			if (new UserDao().modifyUser(sqlPosition)) {/*��position��������һ���û�:ͷ������Ĭ��*/
				/*��tbl_self_schools_favorite��������һ���û���Ĭ��Ŀ���ѧ���廪��ѧ*/
				return new UserDao().modifyUser("insert into tbl_self_schools_favorite(sId,uId) values(1,"+uId+")");
			}
			
		}
		return false;
	}

}
