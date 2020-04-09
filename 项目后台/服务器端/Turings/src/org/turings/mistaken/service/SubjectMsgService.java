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

	//���ݱ�ǩ������Ŀ��û�б�ǩ�Ͳ���ȫ����Ŀ
	public List<SubjectMsg> findSubjectMsgTag(String tag, String subject, int uId) {
		return this.subjectMsgMapper.findSubjectMsgByTag(tag, subject, uId);
	}
	//������һ��
	public SubjectMsg findPreSubjectMsgById(String tag, String subject, int id, int uId) {
		return this.subjectMsgMapper.findPreSubjectMsgById(tag,subject,id,uId);
	}

	//������һ��
	public SubjectMsg findNextSubjectMsgById(String tag, String subject, int id, int uId) {
		return this.subjectMsgMapper.findNextSubjectMsgById(tag,subject,id,uId);
	}
	//�ı���Ŀ��ǩ
	@Transactional(readOnly = false)
	public int changeTagById(String tag, int id) {
		return this.subjectMsgMapper.changeTagById(tag,id);
	}
	//������Ŀѧ��
	@Transactional(readOnly = false)
	public int changeSubjectById(String subject, int id) {
		return this.subjectMsgMapper.changeSubjectById(subject,id);
	}
	//������һ����߱�������Ŀ��һ��
	public SubjectMsg findNextOrFirstSubjectMsgById(String tag, String subject, int id, int uId) {
		SubjectMsg subjectMsg = this.findNextSubjectMsgById(tag, subject, id, uId);
		if(subjectMsg == null) {
			//û����һ����Ŀ�˾Ͳ�����һ��
			subjectMsg = this.subjectMsgMapper.findFirstSubjectMsgById(tag, subject, id, uId);
		}
		return subjectMsg;
	}
	//ɾ����Ŀ
	@Transactional(readOnly = false)
	public int deleteSubjectMsgById(int id) {
		return this.subjectMsgMapper.deleteSubjectMsg(id);
	}
	//�ϴ�����
	@Transactional(readOnly = false)
	public int saveSubjectMsg(SubjectMsg subjectMsg) {
		return this.subjectMsgMapper.saveSubjectMsg(subjectMsg);
	}
	//ͳ��ĳλ�û��Ĵ�������
	public int countForSubjectMsgById(int uId) {
		return this.subjectMsgMapper.countForSubjectMsgById(uId);
	}


}
