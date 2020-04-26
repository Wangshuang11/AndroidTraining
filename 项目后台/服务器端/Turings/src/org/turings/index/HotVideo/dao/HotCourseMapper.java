package org.turings.index.HotVideo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.index.entity.HotCourse;
import org.turings.index.entity.HotVideo;
import org.turings.index.entity.Story;

public interface HotCourseMapper {
	public List<HotCourse> findIndexCourse();
	public List<HotCourse> changeCourse(@Param("a")int a,@Param("b")int b);
	public List<HotCourse> Detail(String a);
}
