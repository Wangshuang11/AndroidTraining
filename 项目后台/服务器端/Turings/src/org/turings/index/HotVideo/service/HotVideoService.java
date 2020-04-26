package org.turings.index.HotVideo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.index.HotVideo.dao.HotCourseMapper;
import org.turings.index.HotVideo.dao.HotVideoMapper;
import org.turings.index.entity.HotCourse;
import org.turings.index.entity.HotVideo;
import org.turings.index.entity.Story;
import org.turings.index.story.dao.StoryMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false)
public class HotVideoService {
	@Resource
	private HotVideoMapper mapper;
	@Resource
	private HotCourseMapper mapper2;
	@Transactional(readOnly=true)
	public List<HotVideo> findVideoAll(){
		return this.mapper.findVideoAll();
	}
	@Transactional(readOnly=true)
	public List<HotCourse> findIndexCourse(){
		return this.mapper2.findIndexCourse();
	}
	
	@Transactional(readOnly=true)
	public List<HotCourse> Detail(String a){
		return this.mapper2.Detail(a);
	}
	@Transactional(readOnly=true)
	public List<HotCourse> changeCourse(int a,int b){
		return this.mapper2.changeCourse(a,b);
	}
	@Transactional(readOnly=true)
	public List<HotVideo> findVideoMore(int begin){
		return this.mapper.findVideoMore(begin);
	}
	
	@Transactional(readOnly=true)
	public List<HotVideo>  SearchVideo(String search){
		return this.mapper.SearchVideo(search);
	}
}
