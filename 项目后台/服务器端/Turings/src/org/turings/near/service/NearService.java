package org.turings.near.service;

import java.util.List;

import org.turings.near.dao.LocationDao;
import org.turings.near.entity.Information;
import org.turings.near.entity.Share;

public class NearService {
	/**
	 * 更新定位，显示其他用户
	 * @param userName
	 * @param lat
	 * @param lng
	 * @return
	 */
	public List browseLoc(String userName,double lat,double lng) {
		return new LocationDao().browseLoc(userName,lat,lng);
	}
	/**
	 * 获取某一用户信息
	 * @param lat
	 * @param lng
	 * @return
	 */
	public Information browseInfo(double lat,double lng) {
		return new LocationDao().browseInfo(lat,lng);
	}
	/**
	 * 查询分享列表
	 * @param userName
	 * @return
	 */
	public List browseShare(String userName) {
		return new LocationDao().browseShareTitle(userName);
	}
	/**
	 * 查询分享文本
	 * @param title
	 * @return
	 */
	public Share browseShareContent(String title) {
		return new LocationDao().browseShareContext(title);
	}
	/**
	 * 增加一条分享
	 * @param userName
	 * @param title
	 * @param content
	 * @param background
	 * @return
	 */
	public int insertShare(String userName,String title,String content,String background) {
		return new LocationDao().insertShare(userName, title, content, background);
	}
}
