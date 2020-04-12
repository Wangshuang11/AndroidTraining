package org.turings.myself.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.login.entity.User;
import org.turings.myself.dao.MyselfMapper;
import org.turings.myself.entity.CourseInfo;

@Service
@Transactional(readOnly=true)
public class MyselfService {
	@Resource
	private MyselfMapper myselfMapper;
	
	//加载全部的课程
	public List<CourseInfo> listAllCourses() {
		return this.myselfMapper.listAllCourses();
	}
	
}
