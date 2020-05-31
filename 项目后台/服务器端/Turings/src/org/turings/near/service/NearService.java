package org.turings.near.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.near.dao.CommentMapper;
import org.turings.near.dao.InformationMapper;
import org.turings.near.dao.PositionMapper;
import org.turings.near.dao.ShareMapper;
import org.turings.near.entity.CommentDetailBean;
import org.turings.near.entity.Information;
import org.turings.near.entity.Share;

@Service
@Transactional(readOnly=true)
public class NearService {

	@Autowired
	private InformationMapper informationMapper;
	@Autowired
	private PositionMapper positionMapper;
	@Autowired
	private ShareMapper shareMapper;
	@Autowired
	private CommentMapper commentMapper;
	
	public List<Integer> ifFid(int id) {
		return informationMapper.ifFid(id);
	}
	
	/**
	 * 
	*<p>Title: showAllComment</p> 
	*<p>Description: 显示文章评论</p> 
	　 * @return
	 */
	public List<CommentDetailBean> showAllComment(){
		return commentMapper.showAllComment(1);
	}
	
	/**
	 * 更新定位，显示其他用户
	 * @param userName
	 * @param lat
	 * @param lng
	 * @return
	 */
	public List browseLoc(String userName,double lat,double lng) {
		return positionMapper.browseLoc(userName, lat, lng);
	}
	/**
	 * 获取某一用户信息
	 * @param lat
	 * @param lng
	 * @return
	 */
	public Information browseInfo(double lat,double lng) {
		return informationMapper.browseInfo(lat,lng);
	}
	public Information browseInfoByName(String name) {
		return informationMapper.browseInfoByName(name);
	}
	/**
	 * 查询分享列表
	 * @param userName
	 * @return
	 */
	public List browseShare(String userName) {
		return shareMapper.browseShareTitle(userName);
	}
	/**
	 * 查询分享文本
	 * @param title
	 * @return
	 */
	public Share browseShareContent(String title) {
		return shareMapper.browseShareContext(title);
	}
	/**
	 * 增加一条分享
	 * @param userName
	 * @param title
	 * @param content
	 * @param background
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insertShare(String userName,String title,String content,String background) {
		System.out.println(userName+title+content+background+"hhhhhhhhhhhhhhhhhhhh");
		return shareMapper.insertShare(userName, title, content, background);
	}
	
}
