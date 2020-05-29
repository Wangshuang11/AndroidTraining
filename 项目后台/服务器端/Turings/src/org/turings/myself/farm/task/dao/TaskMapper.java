package org.turings.myself.farm.task.dao;

import org.apache.ibatis.annotations.Param;
import org.turings.myself.farm.entity.MyTask;

public interface TaskMapper {

	public MyTask getMyTasks(int uid);
	public void refreshLoginTask();
	public void changeWater(@Param("id")int id, @Param("changeNum")int num);
	public void changeLoginStatus(@Param("id")int id, @Param("i")int i);
}
