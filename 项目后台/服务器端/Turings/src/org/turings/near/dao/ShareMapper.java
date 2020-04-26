package org.turings.near.dao;

import java.util.List;

import org.turings.near.entity.Share;

public interface ShareMapper {

	/**
	 * 
	*<p>Title: browseShareTitle</p> 
	*<p>Description: 查询分享列表</p> 
	　 * @param userName
	　 * @return
	 */
	public List browseShareTitle(String userName);
	
	/**
	 * 
	*<p>Title: browseShareContext</p> 
	*<p>Description: 查询一条分享内容</p> 
	　 * @param title
	　 * @return
	 */
	public Share browseShareContext(String title);
	
	/**
	 * 
	*<p>Title: insertShare</p> 
	*<p>Description:添加一条分享内容 </p> 
	　 * @param userName
	　 * @param title
	　 * @param content
	　 * @param background
	　 * @return
	 */
	public int insertShare(String userName,String title,String content,String background);
}
