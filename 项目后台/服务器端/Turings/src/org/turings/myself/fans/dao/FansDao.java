package org.turings.myself.fans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.turings.myself.entity.UserInfo;
import org.turings.myself.util.DbUtil;


/**
 * 
 * use: 数据库查询粉丝相关信息列�?
 * @method  getFansList 获取粉丝列表list
 * @method  getAttentionList 获取关注列表list
 * 
 * 
 * @author 大媛�?
 *  
 */
public class FansDao {
	
	
	/**
	 * 
	 * use: 通过用户id获取粉丝列表
	 * @param id 
	 * @return List
	 * @author 大媛�?
	 * 
	 * 
	 */
	public List<UserInfo> getFansList ( int id ){
		
		List<UserInfo> list = new ArrayList<UserInfo>();
		Connection conn = null;
		
		
		DbUtil dbUtil = DbUtil.getInstance();
		System.out.println("金鑫�?-getFansList"+"打开数据�?");
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select uId , uName , uMotto , uTime , uAvatar from tbl_self_user where uId IN (select fId from tbl_self_fans where uId= ? )";
			
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs=pstm.executeQuery();
			UserInfo user ;
			
			while(rs.next()) {
				// 在这里把查到的粉丝名字加入list
				user = new UserInfo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5));
				list.add(user);
				
				
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
//			try {
//				dbUtil.closeConnection();
//				System.out.println("金鑫�?-getList"+"关闭数据�?");
//			} catch (SQLException e) {
//				System.out.println("金鑫�?-getList"+"数据库关闭异�?");
//			}
		}
		
		
		return list;
		
	}

	
	/**
	 * 
	 * use: 通过用户id获取关注列表
	 * @param id 
	 * @return List
	 * @author 大媛�?
	 * 
	 * 
	 */
	public List<UserInfo> getAttentionList(int id) {
		List<UserInfo> list = new ArrayList<UserInfo>();
		Connection conn = null;
		UserInfo user = null ;
		
//		DbUtil dbUtil = new DbUtil();
		DbUtil dbUtil = DbUtil.getInstance();
		System.out.println("金鑫�?-getAttentionList"+"打开数据�?");
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select uId , uName , uMotto , uTime , uAvatar from tbl_self_user where uId IN (select uId from tbl_self_fans where fId= ? )";
			
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs=pstm.executeQuery();
			
			
			while(rs.next()) {
				
				// 在这里把查到的关注名字加入list
				System.out.println(rs.getInt(1)+" �? "+rs.getString(2)+" �? "+rs.getString(3)+" �? "+rs.getInt(4));
				
				user = new UserInfo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5));
				list.add(user);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getFansList : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getFansList : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
//			try {
//				dbUtil.closeConnection();
//				System.out.println("金鑫�?-getList"+"关闭数据�?");
//			} catch (SQLException e) {
//				System.out.println("金鑫�?-getList"+"数据库关闭异�?");
//			}
			
		}
		return list;
	}

}
