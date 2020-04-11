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
	
	//按照标签tag查找题目信息，如果没有tag，默认搜索全部学科
	@RequestMapping(value ="/findByTag",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findSubjectMsgByTag(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value="uId") int uId) {
		
		List<SubjectMsg> subjectMsgs = this.subjectMsgService.findSubjectMsgTag(tag,subject,uId); 
		for(SubjectMsg subjectMsg:subjectMsgs) {
			System.out.println(subjectMsg.toString());
		}
		return subjectMsgs.toString();
	}
	//查询上一题
	@RequestMapping(value ="/findPre",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findPreSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findPreSubjectMsgById(tag,subject,id,uId);
		System.out.println("打印上一题"+subjectMsg);
		if(subjectMsg != null) {
			return subjectMsg.toString();
		}else {
			return "这是第一道题了".toString();
		}
	}
	//查询下一题
	@RequestMapping(value ="/findNext",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findNextSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findNextSubjectMsgById(tag,subject,id,uId);
		System.out.println("打印下一题"+subjectMsg);
		if(subjectMsg != null) {
			return subjectMsg.toString();
		}else {
			return "这是最后一道题了".toString();
		}
	}
	//更改题目的标签tag
	@RequestMapping(value = "/changeTag",produces="text/json;charset=utf-8")
	@ResponseBody
	public String changeTagOfSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "id") int id) {
		int count = this.subjectMsgService.changeTagById(tag,id);
		if(count > 0) {
			return "修改成功";
		}else {
			return "修改失败";
		}
	}
	
	//更改题目的学科subject
	@RequestMapping(value = "/changeSubject",produces="text/json;charset=utf-8")
	@ResponseBody
	public String changeSubjectOfSubjectMsgById(@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id) {
		int count = this.subjectMsgService.changeSubjectById(subject,id);
		if(count > 0) {
			return "修改成功";
		}else {
			return "修改失败";
		}
	}
	
	//删除当前题目（删除完本题目，自动查询下一题或者第一条同类型题目）
	@RequestMapping(value = "/deleteSubjectMsg",produces="text/json;charset=utf-8")
	@ResponseBody
	public String deleteSubjectMsg(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		//删除当前题目之前，先查出下一题或者本类型题目的第一道题
		SubjectMsg subjectMsg = this.subjectMsgService.findNextOrFirstSubjectMsgById(tag,subject,id,uId);
		System.out.println("下一题或者头一道符合条件的题目"+subjectMsg);
		//删除要删掉的题目
		int n = this.subjectMsgService.deleteSubjectMsgById(id);
		if(n>0) {//表示删除成功，传回题目
			if(subjectMsg == null) {//表示已经没有同类型的题目了，提示重新查找
				return "最后一道题被删除了呢，请重新筛选题目吧".toString();
			}else {
				return subjectMsg.toString();
			}
		}else {
			return "删除失败,请重新删除";
		}
	}
	
	//上传错题
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
		//向数据库中保存错题
//		int n = new MistakenService().uploadWrongQuestionsService(subjectMsg);
		SubjectMsg subjectMsg = new SubjectMsg(1,"数学","集合","填空题",new Date(),"img","","","","","答案",1);
		int n = this.subjectMsgService.saveSubjectMsg(subjectMsg);
		if(n>0) {
			return "上传成功";
		}else {
			return "上传失败";
		}
	}
	
	//统计错题数量
	@RequestMapping(value = "/count",produces="text/json;charset=utf-8")
	@ResponseBody
	public String countForSubjectMsg(@RequestParam(value = "uId") int uId) {
		int count = this.subjectMsgService.countForSubjectMsgById(uId);
		System.out.println("用户保存的错题总数"+count);
		return count+"";
	}
	
}
