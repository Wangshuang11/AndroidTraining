package org.turings.index.school.dao;

import java.util.List;

import org.turings.index.entity.School;

public interface SchoolMapper {
	public List<School> findSchoolByFlag(String flag);
}
