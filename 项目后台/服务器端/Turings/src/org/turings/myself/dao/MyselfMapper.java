package org.turings.myself.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.index.entity.School;
import org.turings.myself.entity.CourseInfo;
import org.turings.myself.entity.Myself;
import org.turings.myself.entity.UserInfo;

public interface MyselfMapper {
	//加载用户全部课程
	public List<CourseInfo> listAllCourses(@Param("uid")int uid);
	//加载用户全部粉丝
	public List<UserInfo> listAllFans(@Param("uid")int uid);
	//加载用户全部关注
	public List<UserInfo> listAllAttentions(@Param("fid")int fid);
	//添加关注
	public int addAttention(@Param("uid")int uid,@Param("fid")int fid);
	//编辑座右铭
	public int editMotto(@Param("uid")int uid,@Param("umotto")int umotto);
	//编辑网名
	public int edituName(@Param("uid")int uid,@Param("uname")int uname);
	//显示学校
	public List<School> listSchools(@Param("uid")int uid);
	//显示用户信息
	public List<Myself> refreshUserInfo(@Param("uid")int uid);
}
