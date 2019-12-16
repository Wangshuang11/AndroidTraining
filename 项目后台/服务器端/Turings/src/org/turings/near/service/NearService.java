package org.turings.near.service;

import java.util.List;

import org.turings.near.dao.LocationDao;
import org.turings.near.entity.Information;
import org.turings.near.entity.Share;

public class NearService {
	/**
	 * ���¶�λ����ʾ�����û�
	 * @param userName
	 * @param lat
	 * @param lng
	 * @return
	 */
	public List browseLoc(String userName,double lat,double lng) {
		return new LocationDao().browseLoc(userName,lat,lng);
	}
	/**
	 * ��ȡĳһ�û���Ϣ
	 * @param lat
	 * @param lng
	 * @return
	 */
	public Information browseInfo(double lat,double lng) {
		return new LocationDao().browseInfo(lat,lng);
	}
	/**
	 * ��ѯ�����б�
	 * @param userName
	 * @return
	 */
	public List browseShare(String userName) {
		return new LocationDao().browseShareTitle(userName);
	}
	/**
	 * ��ѯ�����ı�
	 * @param title
	 * @return
	 */
	public Share browseShareContent(String title) {
		return new LocationDao().browseShareContext(title);
	}
	/**
	 * ����һ������
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
