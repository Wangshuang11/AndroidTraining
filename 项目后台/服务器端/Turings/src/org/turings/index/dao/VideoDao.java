package org.turings.index.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.DbUtil;
import org.turings.index.entity.Video;

public class VideoDao {
	public List<Video> scanAllVideos(String  subject,int begin) throws SQLException {
		List<Video> list = new ArrayList<Video>();
		DbUtil dbUtil = DbUtil.getInstance();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = dbUtil.getConnection();
			String sql = "";
			sql = "select * from tbl_coursevideo where course =? limit "+begin+",?";
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, subject);
			pstm.setInt(2, 3);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Video video = new Video();
				video.setId(rs.getString(1));
				video.setBranch(rs.getString(3));
				video.setCourse(rs.getString(2));
				video.setVideoUrl(rs.getString(5));
				video.setViewCounts(rs.getInt(4));
				video.setStoredCounts(rs.getInt(6));
				video.setSharedCounts(rs.getInt(7));
				list.add(video);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbUtil.closeConnection();
		}
	}
	
	public List<Video> searchVideos(String  subject,String keyword) throws SQLException {
		List<Video> list = new ArrayList<Video>();
		DbUtil dbUtil = DbUtil.getInstance();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = dbUtil.getConnection();
			String sql = "";
			sql = "select * from tbl_coursevideo where course =? and branch like ?";
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, subject);
			pstm.setString(2, "%"+keyword+"%");
			rs = pstm.executeQuery();
			while (rs.next()) {
				Video video = new Video();
				video.setId(rs.getString(1));
				video.setBranch(rs.getString(3));
				video.setCourse(rs.getString(2));
				video.setVideoUrl(rs.getString(5));
				video.setViewCounts(rs.getInt(4));
				video.setStoredCounts(rs.getInt(6));
				video.setSharedCounts(rs.getInt(7));
				list.add(video);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbUtil.closeConnection();
		}
	}

}
