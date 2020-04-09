package org.turings.index.story.dao;

import java.util.List;

import org.turings.index.entity.Story;

public interface StoryMapper {
	public List<Story> findStoryAll();
	public void updateStory(String title,String flag);
}
