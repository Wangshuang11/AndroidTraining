package org.turings.mistaken.controller;


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
	
	//按照标签，题型，学科，时间搜索题目（错题集展示题目用到的筛选条件查询）
	@RequestMapping(value ="/findByTag",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findSubjectMsgByTag(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "date") String date,@RequestParam(value = "type") String type,@RequestParam(value="uId") int uId) {
		System.out.println("tag"+tag+"subject"+subject+"uId"+uId+"date"+date+"type"+type);
		//转换日期格式
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
	//查询错题，按照学科，标签，题型，时间等条件搜索（用于自主组卷的筛选条件，其中标签，题型可能为多个）
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
		if(date.equals("全部") || date.equals("更早")) {
			subjectMsgs = this.subjectMsgService.findSubjectMsgByCondition(subject,tagList,typeList,date,uId);	
		}else {
			int dat = Integer.valueOf(date);
			subjectMsgs = this.subjectMsgService.findSubjectMsgByCondition2(subject,tagList,typeList,dat,uId);
		}
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String subjectMsgList = gson.toJson(subjectMsgs);
		return subjectMsgList;
		
	}
	//查询上一题
	@RequestMapping(value ="/findPre",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findPreSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findPreSubjectMsgById(tag,subject,id,uId);
		System.out.println("打印上一题"+subjectMsg);
		if(subjectMsg != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String subjectMsgStr = gson.toJson(subjectMsg);
			return subjectMsgStr;
		}else {
			return "这已经是第一题了".toString();
		}
	}
	//查询下一题
	@RequestMapping(value ="/findNext",produces="text/json;charset=utf-8")
	@ResponseBody
	public String findNextSubjectMsgById(@RequestParam(value = "tag") String tag,@RequestParam(value = "subject") String subject,@RequestParam(value = "id") int id,@RequestParam(value = "uId") int uId) {
		SubjectMsg subjectMsg = this.subjectMsgService.findNextSubjectMsgById(tag,subject,id,uId);
		System.out.println("打印下一题"+subjectMsg);
		if(subjectMsg != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String subjectMsgStr = gson.toJson(subjectMsg);
			return subjectMsgStr;
		}else {
			return "这已经是最后一道题了".toString();
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
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				String subjectMsgStr = gson.toJson(subjectMsg);
				return subjectMsgStr;
			}
		}else {
			return "删除失败,请重新删除";
		}
	}
	//删除一道或多到题目
	@RequestMapping(value = "/deleteSubjectMsgById",produces="text/json;charset=utf-8")
	@ResponseBody
	public String deleteSubjectById(@RequestParam(value = "ids") String ids) {
		Gson gson = new Gson();
	    List<Integer> obj = gson.fromJson(ids,new TypeToken<List<Integer>>(){}.getType());
	    int n = this.subjectMsgService.deleteSubjectMsgById(obj);
	    if(n>0) {//表示删除成功，传回题目
			return "删除成功";
		}else {
			return null;
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
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String nString = sf.format(new Date());
		Date date = null;
		try {
			date = sf.parse(nString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SubjectMsg subjectMsg = new SubjectMsg(1,"英语","听力","选择题",date,"img","","","","","答案",4);
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
