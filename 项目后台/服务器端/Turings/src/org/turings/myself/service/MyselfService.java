package org.turings.myself.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.login.entity.User;
import org.turings.myself.dao.MyselfMapper;
import org.turings.myself.entity.CourseInfo;
import org.turings.myself.entity.Myself;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.entity.UserInfo;

@Service
@Transactional(readOnly=true)
public class MyselfService {
	@Resource
	private MyselfMapper myselfMapper;
	
	//加载全部的课程
	public List<CourseInfo> listAllCourses(int uid) {
		return this.myselfMapper.listAllCourses(uid);
	}
	//加载全部粉丝
	public List<UserInfo> listAllFans(int uid) {
		return this.myselfMapper.listAllFans(uid);
	}
	//加载全部关注
	public List<UserInfo> listAllAttentions(int fid) {
		return this.myselfMapper.listAllAttentions(fid);
	}
	//添加关注
	public int addAttentions(int attentionId, int fanId) {
		return this.myselfMapper.addAttention(attentionId,fanId);
	}
	//修改座右铭
	public int editMotto(int uid, int uMotto) {
		return this.myselfMapper.editMotto(uid,uMotto);
	}
	//修改网名
	public int edituName(int uid, int uName) {
		return this.myselfMapper.edituName(uid,uName);
	}
	//显示学校
	public List<SchoolInfo> listSchools(int uid) {
		return this.myselfMapper.listSchools(uid);
	}
	public List<Myself> refreshUserInfo(int uid) {
		return this.myselfMapper.refreshUserInfo(uid);
	}
	
	
}
