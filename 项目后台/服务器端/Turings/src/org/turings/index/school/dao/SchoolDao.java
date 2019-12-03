package org.turings.index.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.index.entity.School;
import org.turings.index.util.DbUtil;

public class SchoolDao {
	public List<School> findSchool(String flag) {
		List<School> list = new ArrayList<>();
		Connection conn = DbUtil.getCon();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement("select * from tbl_indexcollege where flag=?");
			pstm.setString(1, flag);
			rs = pstm.executeQuery();
			while (rs.next()) {
				School school = new School();
				String name = rs.getString("name");
				String img = rs.getString("img");
				String url = rs.getString("url");
				String src = rs.getString("src");
				school.setName(name);
				school.setImg(img);
				school.setUrl(url);
				school.setSrc(src);
				list.add(school);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(conn);
		}
		return list;
	}
}
