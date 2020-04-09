package org.turings.near.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PositionMapper {

	/**
	*<p>Title: browseLoc</p> 
	*<p>Description:更新定位并显示其他用户定位 </p> 
	　 * @param userName
	　 * @param lat
	　 * @param lng
	　 * @return
	 */
	public List browseLoc(@Param("userName")String userName,@Param("lat")double lat,@Param("lng")double lng);
}
