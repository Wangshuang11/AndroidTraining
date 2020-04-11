package org.turings.mistaken.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.mistaken.dao.SubjectMsgMapper;
import org.turings.mistaken.entity.SubjectMsg;

@Service
@Transactional(readOnly =true)
public class SubjectMsgService {
	
	@Autowired
	private SubjectMsgMapper subjectMsgMapper;

	//根据标签查找题目，没有标签就查找全部题目
	public List<SubjectMsg> findSubjectMsgTag(String tag, String subject, int uId) {
		return this.subjectMsgMapper.findSubjectMsgByTag(tag, subject, uId);
	}
	//查找上一题
	public SubjectMsg findPreSubjectMsgById(String tag, String subject, int id, int uId) {
		return this.subjectMsgMapper.findPreSubjectMsgById(tag,subject,id,uId);
	}

	//查找下一题
	public SubjectMsg findNextSubjectMsgById(String tag, String subject, int id, int uId) {
		return this.subjectMsgMapper.findNextSubjectMsgById(tag,subject,id,uId);
	}
	//改变题目标签
	@Transactional(readOnly = false)
	public int changeTagById(String tag, int id) {
		return this.subjectMsgMapper.changeTagById(tag,id);
	}
	//更改题目学科
	@Transactional(readOnly = false)
	public int changeSubjectById(String subject, int id) {
		return this.subjectMsgMapper.changeSubjectById(subject,id);
	}
	//查找下一题或者本类型题目第一题
	public SubjectMsg findNextOrFirstSubjectMsgById(String tag, String subject, int id, int uId) {
		SubjectMsg subjectMsg = this.findNextSubjectMsgById(tag, subject, id, uId);
		if(subjectMsg == null) {
			//没有下一道题目了就查找上一题
			subjectMsg = this.subjectMsgMapper.findFirstSubjectMsgById(tag, subject, id, uId);
		}
		return subjectMsg;
	}
	//删除题目
	@Transactional(readOnly = false)
	public int deleteSubjectMsgById(int id) {
		return this.subjectMsgMapper.deleteSubjectMsg(id);
	}
	//上传错题
	@Transactional(readOnly = false)
	public int saveSubjectMsg(SubjectMsg subjectMsg) {
		return this.subjectMsgMapper.saveSubjectMsg(subjectMsg);
	}
	//统计某位用户的错题数量
	public int countForSubjectMsgById(int uId) {
		return this.subjectMsgMapper.countForSubjectMsgById(uId);
	}


}
