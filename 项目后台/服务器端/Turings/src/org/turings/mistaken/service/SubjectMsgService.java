package org.turings.mistaken.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.mistaken.dao.SubjectMsgMapper;
import org.turings.mistaken.entity.SubjectMsg;

import javafx.scene.chart.PieChart.Data;


@Service
@Transactional(readOnly =true)
public class SubjectMsgService {
	
	@Autowired
	private SubjectMsgMapper subjectMsgMapper;

	//���ݱ�ǩ������Ŀ��û�б�ǩ�Ͳ���ȫ����Ŀ
	public List<SubjectMsg> findSubjectMsgTag(String tag, String subject, Date dat, String type, int uId) {
		return this.subjectMsgMapper.findSubjectMsgByTag(tag, subject,dat,type,uId);
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
	@Transactional(readOnly = false)
	//����ɾ��
	public int deleteSubjectMsgById(List<Integer> obj) {
		return this.subjectMsgMapper.deleteSubjectMsgById(obj);
	}
	//��ѯȫ��(����)ʱ�������µ���Ŀ
	public List<SubjectMsg> findSubjectMsgByCondition(String subject, List<String> tagList, List<String> typeList,String date,
			int uId) {
		List<SubjectMsg> subjectMsgs = null;
		if(date.equals("ȫ��")) {
			if(tagList.contains("ȫ��")) {
				if(typeList.contains("ȫ��")) {
					subjectMsgs = this.subjectMsgMapper.findSubjectMsgByCondition(subject,uId);
				}else {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTypes(subject,typeList, uId);
				}
			}else {
				if(typeList.contains("ȫ��")) {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTags(subject,tagList, uId);
				}else {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTagsAndTypes(subject,tagList,typeList, uId);
				}
			}
			
		}else {//����
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			Date dat=null;
			try {
				dat = sf.parse("2018");
				sf = new SimpleDateFormat("yyyy-MM-dd");
				String nString = sf.format(dat);
				dat = sf.parse(nString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(tagList.contains("ȫ��")) {
				if(typeList.contains("ȫ��")) {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByMoreTime(subject,dat,uId);
				}else {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTypesAndMoreTime(subject,typeList,dat,uId);
				}
			}else {
				if(typeList.contains("ȫ��")) {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTagsAndMoreTime(subject,tagList,dat,uId);
				}else {
					subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTagsAndTypesAndMoreTime(subject,tagList,typeList,dat,uId);
				}
			}
		}
		return subjectMsgs;
	}
	//��ѯ������������Ŀ
	public List<SubjectMsg> findSubjectMsgByCondition2(String subject, List<String> tagList,List<String> typeList,int i,
			int uId) {
		List<SubjectMsg> subjectMsgs = null;
		if(tagList.contains("ȫ��")) {
			if(typeList.contains("ȫ��")) {
				subjectMsgs = this.subjectMsgMapper.findSubjectMsgByCondition2(subject,i,uId);
			}else {
				subjectMsgs =this.subjectMsgMapper.findSubjectMsgByCondition2ByTypes(subject,typeList,i, uId);
			}
		}else {
			if(typeList.contains("ȫ��")) {
				subjectMsgs =this.subjectMsgMapper.findSubjectMsgByConditionByTags2(subject,tagList,i, uId);
			}else {
				subjectMsgs =this.subjectMsgMapper.findSubjectMsgByCondition2ByTagsAndTypes(subject,tagList,typeList,i, uId);
			}
		}
		return subjectMsgs;
	}
	

}
