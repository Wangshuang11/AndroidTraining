package org.turings.myself.editinfo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.turings.myself.util.DbUtil;

public class EditUserInfoDao {

	public String editName(int uid, String uname) {
		String result = "false";

		//TODO 查询数据库，如果没有关注，加�?条关注，返回"true"；如果关注过了，返回“alreadyFollowed�?
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;		
		
		System.out.println("金鑫�?-改昵称editName"+"打开数据�?");
		
		
		try {
			conn = dbUtil.getConnection();
			String sql = "update tbl_self_user set uName=? where uId=?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, uname);
			pstm.setInt(2, uid);
			int rs=pstm.executeUpdate();
			if(rs==1) {
				result = "true";
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫�?-?"+"关闭数据�?");
			} catch (SQLException e) {
				System.out.println("金鑫�?-?"+"数据库关闭异�?");
			}
		}
		
		
		return result;
	}

	public String editMotto(int uid, String umotto) {
		String result = "false";

		//TODO 查询数据库，如果没有关注，加�?条关注，返回"true"；如果关注过了，返回“alreadyFollowed�?
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;		
		
		System.out.println("金鑫�?-改昵称editName"+"打开数据�?");
		
		
		try {
			conn = dbUtil.getConnection();
			String sql = "update tbl_self_user set uMotto=? where uId=?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, umotto);
			pstm.setInt(2, uid);
			int rs=pstm.executeUpdate();
			if(rs==1) {
				result = "true";
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫�?-?"+"关闭数据�?");
			} catch (SQLException e) {
				System.out.println("金鑫�?-?"+"数据库关闭异�?");
			}
		}
		
		
		return result;
	}

}
