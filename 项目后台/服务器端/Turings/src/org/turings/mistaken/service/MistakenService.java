package org.turings.mistaken.service;

import java.util.List;

import org.turings.mistaken.dao.MisTakenDao;
import org.turings.mistaken.entity.SubjectMsg;

public class MistakenService {

	//上传错题
	public int uploadWrongQuestionsService(SubjectMsg subjectMsg) {
		return new MisTakenDao().uploadWrongQuestionsDao(subjectMsg);
	}
	//按照学科(数学、语文、英语)和tag查找题目
	public List<SubjectMsg> searchSubjectMsgBySubjectAndTagService(String subject,int uId, String tag){
		return new MisTakenDao().searchSubjectMsgBySubjectAndTagDao(subject,uId,tag);
	}
	//按照学科查找题目
	public List<SubjectMsg> searchSubjectMsgBySubjectAndTagService(String subject,int uId){
		return new MisTakenDao().searchSubjectMsgBySubjectAndTagDao(subject,uId);
	}
	//查询上一题
	public SubjectMsg searchPreSubjectService(int id,String subject,String tag,int uId) {
		return new MisTakenDao().searchPreSubjectDao(id, subject, tag, uId);
	}
	//查询下一题
	public SubjectMsg searchNextSubjectService(int id,String subject,String tag,int uId) {
		return new MisTakenDao().searchNextSubjectDao(id, subject, tag, uId);
	}
	//更改题目标签
	public int changeTagOfSubjectService(int id,String tag) {
		return new MisTakenDao().changeTagOfSubject(id, tag);
	}
	//更改题目学科
	public int changeSjtOfSubjectService(int id,String subject) {
		return new MisTakenDao().changeSjtOfSubject(id, subject);
	}
	//在删除题目之前先查询出被删除题目的下一道题或者此类题目的第一道题
	public SubjectMsg searchNextdeletedSubjectService(int id,String subject,String tag,int uId) {
		return new MisTakenDao().searchNextdeletedSubjectDao(id, subject, tag, uId);
	}
	//删除指定id的题目
	public int deleteSubjectService(int id) {
		return new MisTakenDao().deleteSubjectDao(id);
	}
	
	//返回所有错题的数量
	public int countAllWrongQuestions(int uId) {
		return new MisTakenDao().countAllWrongQuestions("select * from tbl_mistaken where uId="+1+"");
	}
}
