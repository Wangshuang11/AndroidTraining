package org.turings.mistaken.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.turings.DbUtil;
import org.turings.mistaken.entity.SubjectMsg;

public class MisTakenDao {

	//���û��������Ŀ�������ݿ�
	public int uploadWrongQuestionsDao(SubjectMsg subjectMsg) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dbUtil.getConnection();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //��ʽ��Ϊ����/ʱ���ַ���
            String registTime =sdf.format(subjectMsg.getTime());
			String sql = "insert into tbl_mistaken(subject,tag,type,time,titleImg,optionA,optionB,optionC,optionD,answer,uId) values(?,?,?,?,?,?,?,?,?,?,?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1,subjectMsg.getSubject());
			stmt.setString(2,subjectMsg.getTag());
			stmt.setString(3,subjectMsg.getType());
			stmt.setString(4,registTime);
			stmt.setString(5,subjectMsg.getTitleImg());
			stmt.setString(6,subjectMsg.getOptionA());
			stmt.setString(7,subjectMsg.getOptionB());
			stmt.setString(8,subjectMsg.getOptionC());
			stmt.setString(9,subjectMsg.getOptionD());
			stmt.setString(10,subjectMsg.getAnswer());
			stmt.setInt(11, subjectMsg.getuId());
			int n = stmt.executeUpdate();
			if(n>0) {
				return n;
			}
			dbUtil.closeConnection();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//����ѧ��(��ѧ�����ģ�Ӣ�)�����ݿ��в�����Ŀ
	public List<SubjectMsg> searchSubjectMsgBySubjectAndTagDao(String subject,int uId,String tag){
		List<SubjectMsg> subjectMsgs = new ArrayList<SubjectMsg>();
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			if(tag == null || tag.equals("")) {
				String sql = "select * from tbl_mistaken where subject = ? and uId = ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1,subject);
				pstm.setInt(2,uId);
			}else {
				String sqls = "select * from tbl_mistaken where subject = ? and uId = ? and tag = ?";
				pstm = conn.prepareStatement(sqls);
				pstm.setString(1,subject);
				pstm.setInt(2,uId);
				pstm.setString(3,tag);
			}
			rs = pstm.executeQuery();
			while(rs.next()) {
				SubjectMsg subjectMsg = new SubjectMsg(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate("time"),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getInt(12));
				subjectMsgs.add(subjectMsg);
			}
			dbUtil.closeConnection();
			return subjectMsgs;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//����ѧ�Ʋ�����Ŀ
	public List<SubjectMsg> searchSubjectMsgBySubjectAndTagDao(String subject, int uId) {
		List<SubjectMsg> subjectMsgs = new ArrayList<SubjectMsg>();
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_mistaken where subject = ? and uId = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,subject);
			pstm.setInt(2,uId);
			rs = pstm.executeQuery();
			while(rs.next()) {
				SubjectMsg subjectMsg = new SubjectMsg(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate("time"),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getInt(12));
				subjectMsgs.add(subjectMsg);
			}
			dbUtil.closeConnection();
			return subjectMsgs;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//��ѯ��һ��
	public SubjectMsg searchPreSubjectDao(int id,String subject,String tag,int uId) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_mistaken where id = (select max(id) from tbl_mistaken where id < ? and subject = ? and tag = ? and uId = ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,id);
			pstm.setString(2,subject);
			pstm.setString(3,tag);
			pstm.setInt(4,uId);
			rs = pstm.executeQuery();
			if(rs.next()) {
				SubjectMsg subjectMsg = new SubjectMsg(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate("time"),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getInt(12));
				return subjectMsg;
			}
			dbUtil.closeConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//��ѯ��һ��
	public SubjectMsg searchNextSubjectDao(int id,String subject,String tag,int uId) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_mistaken where id = (select min(id) from tbl_mistaken where id > ? and subject = ? and tag = ? and uId = ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,id);
			pstm.setString(2,subject);
			pstm.setString(3,tag);
			pstm.setInt(4,uId);
			rs = pstm.executeQuery();
			if(rs.next()) {
				SubjectMsg subjectMsg = new SubjectMsg(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate("time"),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getInt(12));
				return subjectMsg;
			}
			dbUtil.closeConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//������Ŀ��ǩ
	public int changeTagOfSubject(int id,String tag) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "update tbl_mistaken set tag = ? where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,tag);
			pstm.setInt(2,id);
			int k = pstm.executeUpdate();
			dbUtil.closeConnection();
			if(k>0) {
				return k;
			}else {
				return 0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//������Ŀѧ��
	public int changeSjtOfSubject(int id,String subject) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "update tbl_mistaken set subject = ? where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,subject);
			pstm.setInt(2,id);
			int k = pstm.executeUpdate();
			dbUtil.closeConnection();
			if(k>0) {
				return k;
			}else {
				return 0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//��ɾ����Ŀ֮ǰ�Ȳ�ѯ����ɾ����Ŀ����һ������ߴ�����Ŀ�ĵ�һ����
	public SubjectMsg searchNextdeletedSubjectDao(int id,String subject,String tag,int uId) {
		SubjectMsg subjectMsg = null;
		//�Ȳ�ѯ�Ƿ�����һ�⣬����ش�
		subjectMsg = searchNextSubjectDao(id, subject, tag, uId);
		if(subjectMsg == null) {//���û����һ����ش�ͬ������Ŀ��һ��
			DbUtil dbUtil = DbUtil.getInstance();
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				conn = dbUtil.getConnection();
				String sql = "select * from tbl_mistaken where subject = ? and uId = ? and tag = ? and id != ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1,subject);
				pstm.setInt(2,uId);
				pstm.setString(3,tag);
				pstm.setInt(4,id);
				rs = pstm.executeQuery();
				if(rs.next()) {
					subjectMsg = new SubjectMsg(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate("time"),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getInt(12));
				}
				dbUtil.closeConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return subjectMsg;
	}
	//ɾ����Ŀ����������һ�⣬�����һ��Ϊ����ô������һ��
	public int deleteSubjectDao(int id) {
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "delete from tbl_mistaken where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,id);
			int n = pstm.executeUpdate();
			dbUtil.closeConnection();
			if(n>0) {
				return n;
			}else {
				return 0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	//�������д��������
	public int countAllWrongQuestions(String sql) {
		int num=0;
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn = null;
		try {
			conn = dbUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				num++;
			} 
			dbUtil.closeConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return num;
	}

	
}
