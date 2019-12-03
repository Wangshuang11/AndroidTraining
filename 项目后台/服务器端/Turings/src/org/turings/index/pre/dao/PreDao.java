package org.turings.index.pre.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.index.entity.Pre;
import org.turings.index.util.DbUtil;

public class PreDao {
	private String indent = "\t\t\t\t";
	private String newline = "\n";

	public List<Pre> findAll() {
		List<Pre> list = new ArrayList<>();
		Connection conn = DbUtil.getCon();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement("select * from tbl_indexname");
			rs = pstm.executeQuery();
			while (rs.next()) {
				Pre pre = new Pre();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String img = rs.getString("img");
				String src = rs.getString("src");
				int paracount = rs.getInt("paracount");
				pre.setTitle(name);
				pre.setImg(img);
				pre.setSrc(src);
				String content = findContent(id, paracount, conn);
				pre.setContent(content);
				list.add(pre);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(conn);
		}
		return list;
	}

	public String findContent(int id, int paracount, Connection conn) {
		StringBuffer sbf = new StringBuffer();
		try {
			for (int i = 1; i <= paracount; i++) {
				sbf.append(this.indent);
				PreparedStatement pstm2 = conn
						.prepareStatement("select * from tbl_indexcontent " + "where flag=? && flagt=?");
				pstm2.setInt(1, id);
				pstm2.setInt(2, i);
				ResultSet rs2 = pstm2.executeQuery();
				while (rs2.next()) {
					String string = rs2.getString("content");
					sbf.append(string);
				}
				sbf.append(this.newline);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sbf.toString();
	}
}
