package org.turings.mistaken.service;

import java.util.List;

import org.turings.mistaken.dao.MisTakenDao;
import org.turings.mistaken.entity.SubjectMsg;

public class MistakenService {

	//�ϴ�����
	public int uploadWrongQuestionsService(SubjectMsg subjectMsg) {
		return new MisTakenDao().uploadWrongQuestionsDao(subjectMsg);
	}
	//����ѧ��(��ѧ�����ġ�Ӣ��)��tag������Ŀ
	public List<SubjectMsg> searchSubjectMsgBySubjectAndTagService(String subject,int uId, String tag){
		return new MisTakenDao().searchSubjectMsgBySubjectAndTagDao(subject,uId,tag);
	}
	//����ѧ�Ʋ�����Ŀ
	public List<SubjectMsg> searchSubjectMsgBySubjectAndTagService(String subject,int uId){
		return new MisTakenDao().searchSubjectMsgBySubjectAndTagDao(subject,uId);
	}
	//��ѯ��һ��
	public SubjectMsg searchPreSubjectService(int id,String subject,String tag,int uId) {
		return new MisTakenDao().searchPreSubjectDao(id, subject, tag, uId);
	}
	//��ѯ��һ��
	public SubjectMsg searchNextSubjectService(int id,String subject,String tag,int uId) {
		return new MisTakenDao().searchNextSubjectDao(id, subject, tag, uId);
	}
	//������Ŀ��ǩ
	public int changeTagOfSubjectService(int id,String tag) {
		return new MisTakenDao().changeTagOfSubject(id, tag);
	}
	//������Ŀѧ��
	public int changeSjtOfSubjectService(int id,String subject) {
		return new MisTakenDao().changeSjtOfSubject(id, subject);
	}
	//��ɾ����Ŀ֮ǰ�Ȳ�ѯ����ɾ����Ŀ����һ������ߴ�����Ŀ�ĵ�һ����
	public SubjectMsg searchNextdeletedSubjectService(int id,String subject,String tag,int uId) {
		return new MisTakenDao().searchNextdeletedSubjectDao(id, subject, tag, uId);
	}
	//ɾ��ָ��id����Ŀ
	public int deleteSubjectService(int id) {
		return new MisTakenDao().deleteSubjectDao(id);
	}
	
	//�������д��������
	public int countAllWrongQuestions(int uId) {
		return new MisTakenDao().countAllWrongQuestions("select * from tbl_mistaken where uId="+1+"");
	}
}
