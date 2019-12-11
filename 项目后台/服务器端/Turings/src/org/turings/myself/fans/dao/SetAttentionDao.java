package org.turings.myself.fans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.turings.myself.util.DbUtil;

public class SetAttentionDao {

	public String setAttention(int aid, int fid) {
		String result = "false";

		//TODO 查询数据库，如果没有关注，加�?条关注，返回"true"；如果关注过了，返回“alreadyFollowed�?
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;		
		
		System.out.println("金鑫�?-getFansList"+"打开数据�?");
		
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_self_fans where uId= ? and fid= ? ";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, aid);
			pstm.setInt(2, fid);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1)==aid && rs.getInt(2)==fid) {
					result = "alreadyFollowed";
					return result;
				}
			}
			//TODO 查不到�?�么�?
			
			String sql2 ="insert into tbl_self_fans(uId,fId) values(?,?)";
			PreparedStatement pstm2 = conn.prepareStatement(sql2);
			pstm2.setInt(1, aid);
			pstm2.setInt(2, fid);
			
			int rs2=pstm2.executeUpdate();
			if(rs2==1) {
				result = "true";
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫�?-getList"+"关闭数据�?");
			} catch (SQLException e) {
				System.out.println("金鑫�?-getList"+"数据库关闭异�?");
			}
		}
		
		
		return result;
		
	}

	

}
