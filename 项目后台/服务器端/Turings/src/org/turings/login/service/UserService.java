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
		String sql="insert into tbl_self_user(uTel,uName,uPwd) values('"+uTel+"','"+uName+"','"+uPwd+"')";
		return new UserDao().modifyUser(sql);
	}

}
