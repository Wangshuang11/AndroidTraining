package org.turings.mistaken.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.mistaken.entity.SubjectMsg;

public interface SubjectMsgMapper {
	//根据标签查找题目，没有标签就查找全部题目
	public List<SubjectMsg> findSubjectMsgByTag(@Param("tag")String tag,@Param("subject")String subject,@Param("uId")int uId);
	//查找上一题
	public SubjectMsg findPreSubjectMsgById(@Param("tag")String tag,@Param("subject")String subject,@Param("id")int id,@Param("uId")int uId);
	//查找下一题
	public SubjectMsg findNextSubjectMsgById(@Param("tag")String tag,@Param("subject")String subject,@Param("id")int id,@Param("uId")int uId);
	//改变题目标签
	public int changeTagById(@Param("tag")String tag,@Param("id")int id);
	//更改题目学科
	public int changeSubjectById(@Param("subject")String subject,@Param("id")int id);
	//从表中查出符合条件的第一条数据
	public SubjectMsg findFirstSubjectMsgById(@Param("tag")String tag,@Param("subject")String subject,@Param("id")int id,@Param("uId")int uId);
	//删除题目
	public int deleteSubjectMsg(int id);
	//上传错题
	public int saveSubjectMsg(SubjectMsg subjectMsg);
	//统计错题数量
	public int countForSubjectMsgById(int uId);
}
