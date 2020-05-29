/**
 * 
 */
/**
 * @author 大媛媛
 *
 */
package org.turings.myself.farm.quartz;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.turings.myself.farm.task.service.TaskService;

@Service
public class RefreshTeskEveryday {
	
	@Resource
	private TaskService TaskServiceImpl;
	@Scheduled(cron = "0 0 2 ? * *")
	public void showTime(){
		String time  = new Date().toLocaleString();
		TaskServiceImpl.refreshTaskTable();
		
		System.out.println("当前时间："+time+"登录任务已更新");
	}
}
