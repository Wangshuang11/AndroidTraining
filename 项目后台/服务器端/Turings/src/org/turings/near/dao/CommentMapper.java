package org.turings.near.dao;

import java.util.List;

import org.turings.near.entity.CommentDetailBean;

public interface CommentMapper {

	/**
	 * 
	*<p>Title: shouAllComment</p> 
	*<p>Description: 某一分享文章的全部评论</p> 
	　 * @param sId
	　 * @return
	 */
	public List<CommentDetailBean> showAllComment(int sId);
}
