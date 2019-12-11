package org.turings.near.service;

import java.util.List;

import org.turings.near.dao.LocationDao;
import org.turings.near.entity.Information;
import org.turings.near.entity.Share;

public class NearService {
	public List browseLoc(String userName) {
		return new LocationDao().browseLoc(userName);
	}
	public Information browseInfo(double lat,double lng) {
		return new LocationDao().browseInfo(lat,lng);
	}
	public List browseShare(String userName) {
		return new LocationDao().browseShareTitle(userName);
	}
	public Share browseShareContent(String title) {
		return new LocationDao().browseShareContext(title);
	}
	
	public int insertShare(String userName,String title,String content,String background) {
		return new LocationDao().insertShare(userName, title, content, background);
	}
}
