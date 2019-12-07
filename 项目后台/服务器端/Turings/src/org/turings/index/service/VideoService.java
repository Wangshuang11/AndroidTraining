package org.turings.index.service;



import java.sql.SQLException;
import java.util.List;

import org.turings.index.dao.VideoDao;
import org.turings.index.entity.Video;

public class VideoService {
	public List<Video> scanAllVideos(String subject,int begin) throws SQLException{
		return new VideoDao().scanAllVideos(subject,begin);
	}
	
	public List<Video> searchVideos(String subject,String keyword) throws SQLException {
		return new VideoDao().searchVideos(subject, keyword);
	}
}
