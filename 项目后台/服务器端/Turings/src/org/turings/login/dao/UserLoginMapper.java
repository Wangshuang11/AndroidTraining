package org.turings.login.dao;

import org.apache.ibatis.annotations.Param;
import org.turings.login.entity.User;

public interface UserLoginMapper {

	public User queryUser(User user);

	public int modifyUser(User user);

	public int insertUser(User user);

	public int insertUserPosition(@Param("uName")String uName, @Param("portrait")String portrait, @Param("lat")String lat, @Param("lng")String lng);

	public int insertUserSchool(@Param("sId")int sId, @Param("uId")int uId);

}
