package org.turings.index.story.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.index.entity.Story;
import org.turings.index.util.DbUtil;

public class StoryDao {
	public List<Story> findStoryAll() {
		List<Story> list = new ArrayList<>();
		Connection conn = DbUtil.getCon();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement("select * from tbl_story");
			rs = pstm.executeQuery();
			while (rs.next()) {
				Story story = new Story();
				story.setTitle(rs.getString("title"));
				story.setImg1(rs.getString("img1"));
				story.setImg2(rs.getString("img2"));
				story.setImg3(rs.getString("img3"));
				story.setNum(rs.getString("num"));
				story.setContent(rs.getString("content"));
				story.setFlag(false);
				list.add(story);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(conn);
		}
		return list;
	}

	public String updateStory(String title, String flag) {
		Connection conn = DbUtil.getCon();
		PreparedStatement pstm = null;
		int num = 0;
		try {
			pstm = conn.prepareStatement("select * from tbl_story where title=?");
			pstm.setString(1, title);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				num = Integer.parseInt(rs.getString("num"));
			}
			switch (flag) {
			case "1":
				PreparedStatement pstmt = conn.prepareStatement("update tbl_story set num=? where title=?");
				num++;
				pstmt.setString(1, String.valueOf(num));
				pstmt.setString(2, title);
				int n = pstmt.executeUpdate();
				if (n > 0) {
					return "one" + num;
				}
				break;
			case "2":
				PreparedStatement pstmt2 = conn.prepareStatement("update tbl_story set num=? where title=?");
				num--;
				if (num < 0) {
					num = 0;
				}
				String stm2 = num + "";
				pstmt2.setString(1, stm2);
				pstmt2.setString(2, title);
				int n2 = pstmt2.executeUpdate();
				if (n2 > 0) {
					return "two" + num;
				}
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
