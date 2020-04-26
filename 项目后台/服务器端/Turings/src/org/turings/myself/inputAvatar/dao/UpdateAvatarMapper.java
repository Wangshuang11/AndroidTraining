package org.turings.myself.inputAvatar.dao;

import org.apache.ibatis.annotations.Param;

public interface UpdateAvatarMapper {

	public void uptoDate(@Param("id")int id, @Param("url")String url) ;
}
