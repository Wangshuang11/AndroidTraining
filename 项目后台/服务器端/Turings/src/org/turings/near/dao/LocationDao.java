package org.turings.near.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.DbUtil;
import org.turings.near.entity.Information;
import org.turings.near.entity.Position;
import org.turings.near.entity.Share;

/**
 * 附近的人
 * @author 吕怡浩
 *
 */
public class LocationDao {
	/**
	 * 更新定位并显示其他用户定位
	 * @author 吕怡浩
	 *
	 */
	public List browseLoc(String userName,double lat,double lng) {
		List<Position> posList = new ArrayList<>();
		DbUtil dbUtil = DbUtil.getInstance();
		Connection con=null;
		PreparedStatement pstm = null;
		try {
			con=dbUtil.getConnection();
			pstm = con.prepareStatement("select * from tbl_position");
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				if (rs.getString(2)!=userName) {
					Position pos = new Position();
					pos.setId(rs.getInt(1));
					pos.setUserName(rs.getString(2));
					pos.setPortrait(rs.getString(3));
					pos.setLat(rs.getDouble(4));
					pos.setLng(rs.getDouble(5));
					pos.setType(rs.getInt(6));
					posList.add(pos);
				}
			}
			return posList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				dbUtil.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 显示其他用户的信息页
	 * @param lat
	 * @param lng
	 * @return
	 */
	public Information browseInfo(double lat,double lng) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection con=null;
		Information info = null;
		PreparedStatement pstm = null;
		try {
			con=dbUtil.getConnection();
			String sql1="";
			sql1 = "select * from tbl_position where lat=? and lng=? ";
			pstm = con.prepareStatement(sql1);
			pstm.setDouble(1, lat);
			pstm.setDouble(2, lng);
			ResultSet rs = pstm.executeQuery(); 
			while(rs.next()) {
				String sql2="";
				sql2 = "select * from tbl_self_user where uName=? ";
				pstm = con.prepareStatement(sql2);
				pstm.setString(1, rs.getString(2));
				ResultSet rs1 = pstm.executeQuery();
				while(rs1.next()) {
					String sql3 = "select * from tbl_indexcollege where id IN (select sId from tbl_self_schools_favorite where uId= ?) limit 1";
					pstm = con.prepareStatement(sql3);
					pstm.setInt(1,rs.getInt(1));
					ResultSet rs2 = pstm.executeQuery();
					if (rs2.next()) {
						info = new Information();
						info.setId(rs1.getInt(1));
						info.setUserName(rs1.getString("uName"));
						info.setPortrait(rs1.getString("uAvatar"));
						info.setTotalTime(rs1.getInt("uTime"));
						info.setCurrentTime(rs1.getInt("uScore"));
						info.setUniversity(rs2.getString("name"));
						info.setMotto(rs1.getString("uMotto"));
					}
				}
			}
			return info;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				dbUtil.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Information browseInfoByName(String name) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection con=null;
		Information info = null;
		PreparedStatement pstm = null;
		try {
			con=dbUtil.getConnection();
			String sql1="";
			sql1 = "select * from tbl_position where username=? ";
			pstm = con.prepareStatement(sql1);
			pstm.setString(1, name);
			ResultSet rs = pstm.executeQuery(); 
			while(rs.next()) {
				String sql2="";
				sql2 = "select * from tbl_self_user where uName=? ";
				pstm = con.prepareStatement(sql2);
				pstm.setString(1, rs.getString(2));
				ResultSet rs1 = pstm.executeQuery();
				while(rs1.next()) {
					String sql3 = "select * from tbl_indexcollege where id IN (select sId from tbl_self_schools_favorite where uId= ?) limit 1";
					pstm = con.prepareStatement(sql3);
					pstm.setInt(1,rs.getInt(1));
					ResultSet rs2 = pstm.executeQuery();
					if (rs2.next()) {
						info = new Information();
						info.setId(rs1.getInt(1));
						info.setUserName(rs1.getString("uName"));
						info.setPortrait(rs1.getString("uAvatar"));
						info.setTotalTime(rs1.getInt("uTime"));
						info.setCurrentTime(rs1.getInt("uScore"));
						info.setUniversity(rs2.getString("name"));
						info.setMotto(rs1.getString("uMotto"));
					}
				}
			}
			return info;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				dbUtil.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查询分享列表（不包含内容）
	 * @param userName
	 * @return
	 */
	public List browseShareTitle(String userName) {
		System.out.println("browseShareTitle()" + userName);
		DbUtil dbUtil = DbUtil.getInstance();
		Connection con=null;
		List<Share> shareList = new ArrayList<>();
		try {
			con=dbUtil.getConnection();
			String sql = "select * from tbl_share where username=? ";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, userName);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				Share share = new Share();
				share.setUserName(userName);
				share.setShareTitle(rs.getString(3));
				share.setShareContent(rs.getString(4));
				share.setBackground(rs.getString(5));
				System.out.println(userName+rs.getString(3)+rs.getString(4)+rs.getString(5));
				shareList.add(share);
			}
			return shareList;
		}catch (Exception e) {
			e.printStackTrace();
			return shareList;
		}finally {
			try {
				dbUtil.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private char[] getString(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询某一条分享内容
	 * @param title
	 * @return
	 */
	public Share browseShareContext(String title) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection con=null;
		Share share = new Share();
		try {
			con=dbUtil.getConnection();
			PreparedStatement pstm = con.prepareStatement("select * from tbl_share where sharetitle=? ");
			pstm.setString(1, title);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				share.setId(rs.getInt(1));
				share.setUserName(rs.getString(2));
				share.setShareTitle(rs.getString(3));
				share.setShareContent(rs.getString(4));
				share.setBackground(rs.getString(5));
			}
			return share;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				dbUtil.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 添加一条分享
	 * @param userName
	 * @param title
	 * @param content
	 * @param background
	 * @return
	 */
	public int insertShare(String userName,String title,String content,String background) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection con=null;
		Share share = new Share();
		try {
			con=dbUtil.getConnection();
			PreparedStatement pstm = con.prepareStatement("insert into tbl_share (username,sharetitle,sharecontent,background) values (?,?,?,?) ");
			pstm.setString(1, userName);
			pstm.setString(2, title);
			pstm.setString(3, content);
			pstm.setString(4, background);
			int rs = pstm.executeUpdate();
			if (rs>0) {
				return 1;
			}else {
				return 0;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
//			try {
////				dbUtil.closeConnection();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		return 0;
	}
}
