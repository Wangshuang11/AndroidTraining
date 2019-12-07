package org.turings.index.service;

import java.sql.SQLException;
import java.util.List;

import org.turings.index.dao.CourseDao;
import org.turings.index.entity.Course;

import com.sun.org.apache.regexp.internal.recompile;

public class IndexCourseService {
	public List<Course> scanIndexCourse() throws SQLException {
		return new CourseDao().changeCourses();
	}
	public List<Course> scanMostPopularCourse() throws SQLException {
		return new CourseDao().scanMostPupularCourses();
	}
	
	public List<Course> getChildCourses(String paString) throws SQLException {
		return new CourseDao().getChildCourses(paString);
	}

}
