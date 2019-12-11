package org.turings.myself.inputavatar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.turings.myself.util.DbUtil;

public class UpdateAvatarDao {

	public String uptoDate(int id, String url) {

		String result = "false";

		//TODO 查询数据库，如果没有关注，加一条关注，返回"true"；如果关注过了，返回“alreadyFollowed”
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;		
		
		System.out.println("金鑫媛-改昵称editName"+"打开数据库");
		
		
		try {
			conn = dbUtil.getConnection();
			String sql = "update tbl_self_user set uAvatar=? where uId=?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, url);
			pstm.setInt(2, id);
			int rs=pstm.executeUpdate();
			if(rs==1) {
				result = "true";
			}
			
		
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 打开数据库失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 数据库查询失败");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫媛-?"+"关闭数据库");
			} catch (SQLException e) {
				System.out.println("金鑫媛-?"+"数据库关闭异常");
			}
		}
		
		
		return result;
	}
	
	
	
	public String uptoAnotherDate(int id, String url) {

		String result0 = "false";

		//TODO 查询数据库，如果没有关注，加一条关注，返回"true"；如果关注过了，返回“alreadyFollowed”
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;		
		
		System.out.println("金鑫媛-改昵称editName"+"打开数据库");
		
		
		try {
			conn = dbUtil.getConnection();
			
			String sql0 = "update tbl_position set porprait=? where id=?";
			
			PreparedStatement pstm0 = conn.prepareStatement(sql0);
			pstm0.setString(1, url);
			pstm0.setInt(2, id);
			int rs0=pstm0.executeUpdate();
			if(rs0==1) {
				result0 = "true";
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 打开数据库失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from EditUserInfoDao.editName : 数据库查询失败");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫媛-?"+"关闭数据库");
			} catch (SQLException e) {
				System.out.println("金鑫媛-?"+"数据库关闭异常");
			}
		}
		
		
		return result0;
	}

}
