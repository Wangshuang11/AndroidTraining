package org.turings.mistaken.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.mistaken.entity.SubjectMsg;
import org.turings.mistaken.service.SubjectMsgService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/subjectMsg")
public class SubjectMsgController {
	@Resource
	private SubjectMsgService subjectMsgService;
	
	//���ձ�ǩtag������Ŀ��Ϣ�����û��tag��Ĭ������ȫ��ѧ��
	@RequestMapping(value ="/findByTag",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findSubjectMsgByTag(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value="uId") int uId) {
		
		List<SubjectMsg> subjectMsgs = this.subjectMsgService.findSubjectMsgTag(tag,subject,uId); 
		for(SubjectMsg subjectMsg:subjectMsgs) {
			System.out.println(subjectMsg.toString());
		}
		return subjectMsgs.toString();
	}
	//��ѯ��һ��
	@RequestMapping(value ="/findPre",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findPreSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findPreSubjectMsgById(tag,subject,id,uId);
		System.out.println("��ӡ��һ��"+subjectMsg);
		if(subjectMsg != null) {
			return subjectMsg.toString();
		}else {
			return "���ǵ�һ������".toString();
		}
	}
	//��ѯ��һ��
	@RequestMapping(value ="/findNext",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findNextSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findNextSubjectMsgById(tag,subject,id,uId);
		System.out.println("��ӡ��һ��"+subjectMsg);
		if(subjectMsg != null) {
			return subjectMsg.toString();
		}else {
			return "�������һ������".toString();
		}
	}
	//������Ŀ�ı�ǩtag
	@RequestMapping(value = "/changeTag",produces="text/json;charset=utf-8")
	@ResponseBody
	public String changeTagOfSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "id") int id) {
		int count = this.subjectMsgService.changeTagById(tag,id);
		if(count > 0) {
			return "�޸ĳɹ�";
		}else {
			return "�޸�ʧ��";
		}
	}
	
	//������Ŀ��ѧ��subject
	@RequestMapping(value = "/changeSubject",produces="text/json;charset=utf-8")
	@ResponseBody
	public String changeSubjectOfSubjectMsgById(@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id) {
		int count = this.subjectMsgService.changeSubjectById(subject,id);
		if(count > 0) {
			return "�޸ĳɹ�";
		}else {
			return "�޸�ʧ��";
		}
	}
	
	//ɾ����ǰ��Ŀ��ɾ���걾��Ŀ���Զ���ѯ��һ����ߵ�һ��ͬ������Ŀ��
	@RequestMapping(value = "/deleteSubjectMsg",produces="text/json;charset=utf-8")
	@ResponseBody
	public String deleteSubjectMsg(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		//ɾ����ǰ��Ŀ֮ǰ���Ȳ����һ����߱�������Ŀ�ĵ�һ����
		SubjectMsg subjectMsg = this.subjectMsgService.findNextOrFirstSubjectMsgById(tag,subject,id,uId);
		System.out.println("��һ�����ͷһ��������������Ŀ"+subjectMsg);
		//ɾ��Ҫɾ������Ŀ
		int n = this.subjectMsgService.deleteSubjectMsgById(id);
		if(n>0) {//��ʾɾ���ɹ���������Ŀ
			if(subjectMsg == null) {//��ʾ�Ѿ�û��ͬ���͵���Ŀ�ˣ���ʾ���²���
				return "���һ���ⱻɾ�����أ�������ɸѡ��Ŀ��".toString();
			}else {
				return subjectMsg.toString();
			}
		}else {
			return "ɾ��ʧ��,������ɾ��";
		}
	}
	
	//�ϴ�����
	@RequestMapping(value = "/uploadSubejctMsg",produces="text/json;charset=utf-8")
	@ResponseBody
	public String uploadWrongQuestions(HttpServletRequest request) {
//		InputStream in = request.getInputStream();
//		BufferedReader br = new BufferedReader(
//				new InputStreamReader(in, "utf-8"));
//		String subjectJson = br.readLine();
//		in.close();
//		br.close();
//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		SubjectMsg subjectMsg = gson.fromJson(subjectJson, SubjectMsg.class);
		//�����ݿ��б������
//		int n = new MistakenService().uploadWrongQuestionsService(subjectMsg);
		SubjectMsg subjectMsg = new SubjectMsg(1,"��ѧ","����","�����",new Date(),"img","","","","","��",1);
		int n = this.subjectMsgService.saveSubjectMsg(subjectMsg);
		if(n>0) {
			return "�ϴ��ɹ�";
		}else {
			return "�ϴ�ʧ��";
		}
	}
	
	//ͳ�ƴ�������
	@RequestMapping(value = "/count",produces="text/json;charset=utf-8")
	@ResponseBody
	public String countForSubjectMsg(@RequestParam(value = "uId") int uId) {
		int count = this.subjectMsgService.countForSubjectMsgById(uId);
		System.out.println("�û�����Ĵ�������"+count);
		return count+"";
	}
	
}
