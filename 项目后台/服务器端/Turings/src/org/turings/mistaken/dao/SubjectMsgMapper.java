package org.turings.mistaken.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.turings.mistaken.entity.SubjectMsg;

public interface SubjectMsgMapper {
	//���ݱ�ǩ������Ŀ��û�б�ǩ�Ͳ���ȫ����Ŀ
	public List<SubjectMsg> findSubjectMsgByTag(@Param("tag")String tag,@Param("subject")String subject,@Param("date")Date date,@Param("type")String type, @Param("uId")int uId);
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
	//����ɾ��
	public int deleteSubjectMsgById(List<Integer> obj);
	/*
	 * ���·���Ϊ��������еķ���
	 */
	//����ѧ�ƺ��û�id����Ŀ
	public List<SubjectMsg> findSubjectMsgByCondition(@Param("subject")String subject,@Param("uId")int uId);
	//����ѧ�ƣ��û�id�Ͷ������Ͳ���Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTypes(@Param("subject")String subject,@Param("types")List<String> typeList, @Param("uId")int uId);
	//����ѧ�ƣ��û�id�Ͷ��ֱ�ǩ����Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTags(@Param("subject")String subject,@Param("tags")List<String> tagList,@Param("uId")int uId);
	//����ѧ�ƣ��û�id�Ͷ��ֱ�ǩ,�������Ͳ���Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTagsAndTypes(@Param("subject")String subject,@Param("tags")List<String> tagList,
			@Param("types")List<String> typeList, @Param("uId")int uId);
	//����ѧ��,�û�id��ʱ�䣨2018�꼰��ǰʱ�䣩����Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByMoreTime(@Param("subject")String subject,@Param("date")Date dat,@Param("uId")int uId);
	//����ѧ��,�û�id��ʱ�䣨2018�꼰��ǰʱ�䣩�������Ͳ���Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTypesAndMoreTime(@Param("subject")String subject, @Param("types")List<String> typeList,@Param("date")Date dat,
			@Param("uId")int uId);
	//����ѧ��,�û�id��ʱ�䣨2018�꼰��ǰʱ�䣩���ֱ�ǩ����Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTagsAndMoreTime(@Param("subject")String subject,@Param("tags")List<String> tagList,@Param("date")Date dat,
			@Param("uId")int uId);
	//����ѧ��,�û�id��ʱ�䣨2018�꼰��ǰʱ�䣩���ֱ�ǩ�������Ͳ���Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTagsAndTypesAndMoreTime(@Param("subject")String subject,@Param("tags")List<String> tagList,
			@Param("types")List<String> typeList,@Param("date")Date dat,@Param("uId")int uId);
	//����ѧ�ƺ��û�id����ݲ���Ŀ
	public List<SubjectMsg> findSubjectMsgByCondition2(@Param("subject")String subject, @Param("date")int i,@Param("uId")int uId);
	//����ѧ�ƺ��û�id����ݺͶ�����Ͳ���Ŀ
	public List<SubjectMsg> findSubjectMsgByCondition2ByTypes(@Param("subject")String subject,@Param("types")List<String> typeList, @Param("date")int i,
			@Param("uId")int uId);
	//����ѧ�ƺ��û�id����ݺͶ����ǩ����Ŀ
	public List<SubjectMsg> findSubjectMsgByConditionByTags2(@Param("subject")String subject, @Param("tags")List<String> tagList, @Param("date")int i,
			@Param("uId")int uId);
	//����ѧ�ƺ��û�id����ݺͶ����ǩ�Ͷ�����Ͳ���Ŀ
	public List<SubjectMsg> findSubjectMsgByCondition2ByTagsAndTypes(@Param("subject")String subject,@Param("tags")List<String> tagList,
			@Param("types")List<String> typeList,@Param("date")int i,@Param("uId")int uId);
}
