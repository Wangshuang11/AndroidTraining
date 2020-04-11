package org.turings.mistaken.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.mistaken.entity.SubjectMsg;

public interface SubjectMsgMapper {
	//���ݱ�ǩ������Ŀ��û�б�ǩ�Ͳ���ȫ����Ŀ
	public List<SubjectMsg> findSubjectMsgByTag(@Param("tag")String tag,@Param("subject")String subject,@Param("uId")int uId);
	//������һ��
	public SubjectMsg findPreSubjectMsgById(@Param("tag")String tag,@Param("subject")String subject,@Param("id")int id,@Param("uId")int uId);
	//������һ��
	public SubjectMsg findNextSubjectMsgById(@Param("tag")String tag,@Param("subject")String subject,@Param("id")int id,@Param("uId")int uId);
	//�ı���Ŀ��ǩ
	public int changeTagById(@Param("tag")String tag,@Param("id")int id);
	//������Ŀѧ��
	public int changeSubjectById(@Param("subject")String subject,@Param("id")int id);
	//�ӱ��в�����������ĵ�һ������
	public SubjectMsg findFirstSubjectMsgById(@Param("tag")String tag,@Param("subject")String subject,@Param("id")int id,@Param("uId")int uId);
	//ɾ����Ŀ
	public int deleteSubjectMsg(int id);
	//�ϴ�����
	public int saveSubjectMsg(SubjectMsg subjectMsg);
	//ͳ�ƴ�������
	public int countForSubjectMsgById(int uId);
}
