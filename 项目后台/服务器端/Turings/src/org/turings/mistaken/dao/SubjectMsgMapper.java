package org.turings.mistaken.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.turings.mistaken.entity.SubjectMsg;

public interface SubjectMsgMapper {
	//根据标签查找题目，没有标签就查找全部题目
	public List<SubjectMsg> findSubjectMsgByTag(@Param("tag")String tag,@Param("subject")String subject,@Param("date")Date date,@Param("type")String type, @Param("uId")int uId);
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
	//批量删除
	public int deleteSubjectMsgById(List<Integer> obj);
	/*
	 * 以下方法为自主组卷中的方法
	 */
	//按照学科和用户id查题目
	public List<SubjectMsg> findSubjectMsgByCondition(@Param("subject")String subject,@Param("uId")int uId);
	//按照学科，用户id和多种题型查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTypes(@Param("subject")String subject,@Param("types")List<String> typeList, @Param("uId")int uId);
	//按照学科，用户id和多种标签查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTags(@Param("subject")String subject,@Param("tags")List<String> tagList,@Param("uId")int uId);
	//按照学科，用户id和多种标签,多种题型查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTagsAndTypes(@Param("subject")String subject,@Param("tags")List<String> tagList,
			@Param("types")List<String> typeList, @Param("uId")int uId);
	//按照学科,用户id和时间（2018年及以前时间）查题目
	public List<SubjectMsg> findSubjectMsgByConditionByMoreTime(@Param("subject")String subject,@Param("date")Date dat,@Param("uId")int uId);
	//按照学科,用户id和时间（2018年及以前时间）多种题型查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTypesAndMoreTime(@Param("subject")String subject, @Param("types")List<String> typeList,@Param("date")Date dat,
			@Param("uId")int uId);
	//按照学科,用户id和时间（2018年及以前时间）多种标签查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTagsAndMoreTime(@Param("subject")String subject,@Param("tags")List<String> tagList,@Param("date")Date dat,
			@Param("uId")int uId);
	//按照学科,用户id和时间（2018年及以前时间）多种标签多种题型查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTagsAndTypesAndMoreTime(@Param("subject")String subject,@Param("tags")List<String> tagList,
			@Param("types")List<String> typeList,@Param("date")Date dat,@Param("uId")int uId);
	//按照学科和用户id和年份查题目
	public List<SubjectMsg> findSubjectMsgByCondition2(@Param("subject")String subject, @Param("date")int i,@Param("uId")int uId);
	//按照学科和用户id和年份和多个题型查题目
	public List<SubjectMsg> findSubjectMsgByCondition2ByTypes(@Param("subject")String subject,@Param("types")List<String> typeList, @Param("date")int i,
			@Param("uId")int uId);
	//按照学科和用户id和年份和多个标签查题目
	public List<SubjectMsg> findSubjectMsgByConditionByTags2(@Param("subject")String subject, @Param("tags")List<String> tagList, @Param("date")int i,
			@Param("uId")int uId);
	//按照学科和用户id和年份和多个标签和多个题型查题目
	public List<SubjectMsg> findSubjectMsgByCondition2ByTagsAndTypes(@Param("subject")String subject,@Param("tags")List<String> tagList,
			@Param("types")List<String> typeList,@Param("date")int i,@Param("uId")int uId);
}
