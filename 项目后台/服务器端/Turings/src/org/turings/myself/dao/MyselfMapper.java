package org.turings.myself.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.myself.entity.CourseInfo;
import org.turings.myself.entity.Gift;
import org.turings.myself.entity.Myself;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.entity.UserInfo;
import org.turings.myself.entity.Water;
import org.turings.near.entity.Information;

public interface MyselfMapper {
	//添加礼物
	public int addGift(Gift gift);
	public int updateProc(@Param("uid")int uid);
	//显示农场
	public Water showFarm(@Param("uid")int uid);
	//修改农场
	public int updateFarm(Water water);
	//加载用户全部课程
	public List<CourseInfo> listAllCourses(@Param("uid")int uid);
	//加载用户全部粉丝
	public List<UserInfo> listAllFans(@Param("uid")int uid);
	//加载用户全部关注
	public List<UserInfo> listAllAttentions(@Param("fid")int fid);
	//添加关注
	public int addAttention(@Param("uid")int uid,@Param("fid")int fid);
	//取消关注
	public int delAttention(@Param("uid")int uid, @Param("fid")int fid);
	//编辑座右铭
	public int editMotto(@Param("uid")int uid,@Param("umotto")String umotto);
	//编辑网名
	public int editName(@Param("uid")int uid,@Param("uname")String uname);
	//显示粉丝详情
	public Information showFanDetail(@Param("uid")int uid);
	
	
	

	
	//金鑫媛2020/4/14注释
	/*//编辑头像
	public String updateAvatar(@Param("uid")int id,@Param("url")String url);
	//显示学校
	public List<SchoolInfo> listSchools(@Param("uid")int uid);
	//显示用户信息
	public List<Myself> refreshUserInfo(@Param("uid")int uid);*/
	
}
