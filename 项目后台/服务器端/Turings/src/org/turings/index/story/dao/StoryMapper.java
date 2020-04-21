package org.turings.index.story.dao;

import java.util.List;

import org.turings.index.entity.Story;

public interface StoryMapper {
	public List<Story> findStoryAll();
	public int updateStory(String starnum,int id);
}
