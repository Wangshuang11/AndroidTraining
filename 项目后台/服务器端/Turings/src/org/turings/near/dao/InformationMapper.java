package org.turings.near.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.near.entity.Information;

public interface InformationMapper {
	
	/**
	*<p>Title: ifFid</p> 
	*<p>Description: 关注</p> 
	　 * @param id
	　 * @return
	 */
	public List<Integer> ifFid(int id);

	/**
	*<p>Title: browseInfo</p> 
	*<p>Description: 显示其他用户的信息页</p> 
	　 * @param lat
	　 * @param lng
	　 * @return
	 */
	public Information browseInfo(@Param("lat")double lat,@Param("lng")double lng);
	
	/**
	 * 
	*<p>Title: browseInfoByName</p> 
	*<p>Description: 根据姓名查询用户信息页</p> 
	　 * @param name
	　 * @return
	 */
	public Information browseInfoByName(String name);
}