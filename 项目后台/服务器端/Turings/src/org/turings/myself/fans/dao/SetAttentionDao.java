package org.turings.myself.fans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.turings.myself.util.DbUtil;

public class SetAttentionDao {

	public String setAttention(int aid, int fid) {
		String result = "false";

		//TODO 查询数据库，如果没有关注，加一条关注，返回"true"；如果关注过了，返回“alreadyFollowed”
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;		
		
		System.out.println("金鑫媛-getFansList"+"打开数据库");
		
		
		try {
			conn = dbUtil.getConnection();
			
			//先查有没有这个关注
			String sql = "select * from tbl_self_fans where uId= ? and fid= ? ";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, aid);
			pstm.setInt(2, fid);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1)==aid && rs.getInt(2)==fid) {
//					查得到，这个关注不给加，返回already
					result = "alreadyFollowed";
					return result;
				}
			}
			
			
			//查不到加一条关注
			
			String sql2 ="insert into tbl_self_fans(uId,fId) values(?,?)";
			PreparedStatement pstm2 = conn.prepareStatement(sql2);
			pstm2.setInt(1, aid);
			pstm2.setInt(2, fid);
			
			int rs2=pstm2.executeUpdate();
			if(rs2==1) {
				result = "true";
			}
			
			
			
			//然后再在个人表里粉丝加1
			String sql3 = "select count(*) from tbl_self_fans where uId= ? ";
			PreparedStatement pstm3 = conn.prepareStatement(sql3);
			pstm3.setInt(1, aid);
			ResultSet rs3=pstm3.executeQuery();
			while(rs3.next()) {
				
				String sql4 = "update tbl_self_user set uFanscount=? where uId=?";
				
				PreparedStatement pstm4 = conn.prepareStatement(sql4);
				
				int count1 = rs3.getInt(1);
				pstm4.setInt(1,count1 );
				pstm4.setInt(2, aid);
				int rs4=pstm4.executeUpdate();
				result+=":"+rs4;
				
			}
			//然后再在个人表里粉丝加1
			String sql5 = "select count(*) from tbl_self_fans where aId= ? ";
			PreparedStatement pstm5 = conn.prepareStatement(sql5);
			pstm5.setInt(1, aid);
			ResultSet rs5=pstm5.executeQuery();
			while(rs5.next()) {
				
				String sql6 = "update tbl_self_user set uAttentioncount=? where uId=?";
				
				PreparedStatement pstm6 = conn.prepareStatement(sql6);
				
				int count2 = rs5.getInt(1);
				pstm6.setInt(1,count2 );
				pstm6.setInt(2, aid);
				int rs6=pstm6.executeUpdate();
				result+=":"+rs6;
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 打开数据库失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 数据库查询失败");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫媛-getList"+"关闭数据库");
			} catch (SQLException e) {
				System.out.println("金鑫媛-getList"+"数据库关闭异常");
			}
		}
		
		
		return result;
		
	}

	

}
