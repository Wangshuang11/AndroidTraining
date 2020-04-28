package org.turings.mistaken.controller;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.mistaken.entity.SubjectMsg;
import org.turings.mistaken.service.SubjectMsgService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/subjectMsg")
public class SubjectMsgController {
	@Resource
	private SubjectMsgService subjectMsgService;
	
	//���ձ�ǩ�����ͣ�ѧ�ƣ�ʱ��������Ŀ�����⼯չʾ��Ŀ�õ���ɸѡ������ѯ��
	@RequestMapping(value ="/findByTag",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findSubjectMsgByTag(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "date") String date,@RequestParam(value = "type") String type,@RequestParam(value="uId") int uId) {
		System.out.println("tag"+tag+"subject"+subject+"uId"+uId+"date"+date+"type"+type);
		//ת�����ڸ�ʽ
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dat=null;
		if(!date.equals("") && date !=null) {
			try {
				dat = sf.parse(date);
				System.out.println("dat"+dat);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<SubjectMsg> subjectMsgs = this.subjectMsgService.findSubjectMsgTag(tag,subject,dat,type,uId); 
		for(SubjectMsg subjectMsg:subjectMsgs) {
			System.out.println(subjectMsg.toString());
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String subjectMsgList = gson.toJson(subjectMsgs);
		return subjectMsgList;
	}
	//��ѯ���⣬����ѧ�ƣ���ǩ�����ͣ�ʱ�������������������������ɸѡ���������б�ǩ�����Ϳ���Ϊ�����
	@RequestMapping(value ="/findSubForPaper",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findSubjectMsgForPaper(@RequestParam(value = "tags") String tags,@RequestParam(value = "subject") String subject,@RequestParam(value = "date") String date,@RequestParam(value = "types") String types,@RequestParam(value="uId") int uId) {
		Gson gson = new Gson();
	    Map<String,String> tagMap = gson.fromJson(tags,new TypeToken<Map<String, String>>(){}.getType());
	    Map<String,String> typesMap = gson.fromJson(types,new TypeToken<Map<String, String>>(){}.getType());
		List<String> tagList = new ArrayList<>();
		for(String key:tagMap.keySet()) {
			String value = tagMap.get(key);
			tagList.add(value);
		}
		List<String> typeList = new ArrayList<>();
		for(String key:typesMap.keySet()) {
			String value = typesMap.get(key);
			typeList.add(value);
		}
		List<SubjectMsg> subjectMsgs = null;
		if(date.equals("ȫ��") || date.equals("����")) {
			subjectMsgs = this.subjectMsgService.findSubjectMsgByCondition(subject,tagList,typeList,date,uId);	
		}else {
			int dat = Integer.valueOf(date);
			subjectMsgs = this.subjectMsgService.findSubjectMsgByCondition2(subject,tagList,typeList,dat,uId);
		}
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String subjectMsgList = gson.toJson(subjectMsgs);
		return subjectMsgList;
		
	}
	//��ѯ��һ��
	@RequestMapping(value ="/findPre",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findPreSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findPreSubjectMsgById(tag,subject,id,uId);
		System.out.println("��ӡ��һ��"+subjectMsg);
		if(subjectMsg != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String subjectMsgStr = gson.toJson(subjectMsg);
			return subjectMsgStr;
		}else {
			return "���Ѿ��ǵ�һ����".toString();
		}
	}
	//��ѯ��һ��
	@RequestMapping(value ="/findNext",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findNextSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findNextSubjectMsgById(tag,subject,id,uId);
		System.out.println("��ӡ��һ��"+subjectMsg);
		if(subjectMsg != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String subjectMsgStr = gson.toJson(subjectMsg);
			return subjectMsgStr;
		}else {
			return "���Ѿ������һ������".toString();
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
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				String subjectMsgStr = gson.toJson(subjectMsg);
				return subjectMsgStr;
			}
		}else {
			return "ɾ��ʧ��,������ɾ��";
		}
	}
	//ɾ��һ����ൽ��Ŀ
	@RequestMapping(value = "/deleteSubjectMsgById",produces="text/json;charset=utf-8")
	@ResponseBody
	public String deleteSubjectById(@RequestParam(value = "ids") String ids) {
		Gson gson = new Gson();
	    List<Integer> obj = gson.fromJson(ids,new TypeToken<List<Integer>>(){}.getType());
	    int n = this.subjectMsgService.deleteSubjectMsgById(obj);
	    if(n>0) {//��ʾɾ���ɹ���������Ŀ
			return "ɾ���ɹ�";
		}else {
			return null;
		}
	}
	
	//�ϴ�����
	@RequestMapping(value = "/uploadSubejctMsg",produces="text/json;charset=utf-8")
	@ResponseBody
	public String uploadWrongQuestions(HttpServletRequest request) {
		try {
			InputStream in = request.getInputStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(in, "utf-8"));
			String subjectJson = br.readLine();
			in.close();
			br.close();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			SubjectMsg subjectMsg = gson.fromJson(subjectJson, SubjectMsg.class);
			//�����ݿ��б������
			int n = this.subjectMsgService.saveSubjectMsg(subjectMsg);
			System.out.println("ʶ�����ݹ�������"+subjectMsg.getContent());
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//			String nString = sf.format(new Date());
//			Date date = null;
//			try {
//				date = sf.parse(nString);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			SubjectMsg subjectMsg = new SubjectMsg(1,"Ӣ��","����","ѡ����",date,"img","","","","","��",4);
//			int n = this.subjectMsgService.saveSubjectMsg(subjectMsg);
			if(n>0) {
				return "�ϴ��ɹ�";
			}else {
				return "�ϴ�ʧ��";
			}
		} catch (Exception e) {
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
