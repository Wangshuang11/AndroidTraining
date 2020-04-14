package org.turings.myself.school.dao;

import java.util.List;

import org.turings.myself.entity.SchoolInfo;

public interface MySchoolMapper {
//	public List<SchoolInfo> findSchoolByFlag(String flag);

	public List<SchoolInfo> getSchoolsList(String uid);
}
