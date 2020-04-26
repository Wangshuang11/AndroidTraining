package org.turings.index.HotVideo.dao;

import java.util.List;

import org.turings.index.entity.HotVideo;
import org.turings.index.entity.Story;

public interface HotVideoMapper {
	public List<HotVideo> findVideoAll();
	public List<HotVideo> findVideoMore(int begin);
	public List<HotVideo> SearchVideo(String search);
}
