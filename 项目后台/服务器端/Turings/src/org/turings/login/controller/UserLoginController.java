package org.turings.login.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.login.entity.SendSms;
import org.turings.login.entity.User;
import org.turings.login.service.UserLoginService;

@Controller
public class UserLoginController {
	@Resource
	private UserLoginService userLoginService;
	
	@ResponseBody
	@RequestMapping(value="/SendCodeSmsToUser",produces="text/json;charset=utf-8")
	public String sendCodeSmsToUser(@RequestParam("uTel") String uTel) {
		int code=(int) (Math.random()*10000);
		SendSms.sendSms(uTel,code);
		return code+"";
	}
	
	@ResponseBody
	@RequestMapping(value="/UserLoginCheckByName",produces="text/json;charset=utf-8")
	public String userLoginCheckByName(@RequestParam("uName") String uName) {
		User user=new User();
		user.setuName(uName);
		return userLoginService.loginCheckByName(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/UserLoginCheckByPhone",produces="text/json;charset=utf-8")
	public String userLoginCheckByPhone(@RequestParam("uTel") String uTel) {
		User user=new User();
		user.setuTel(uTel);
		return userLoginService.loginCheckByPhone(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/UserLoginCheckByUserPwd",produces="text/json;charset=utf-8")
	public String userLoginCheckByUserPwd(@RequestParam("uName") String uName,@RequestParam("uPwd") String uPwd) {
		User user=new User();
		user.setuName(uName);
		user.setuPwd(uPwd);
		return userLoginService.loginCheckByUserPwd(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/UserModifyPwdByPhone",produces="text/json;charset=utf-8")
	public String userModifyPwdByPhone(@RequestParam("uTel") String uTel,@RequestParam("uPwd") String uPwd) {
		User user=new User();
		user.setuTel(uTel);
		user.setuPwd(uPwd);
		return userLoginService.modifyPwdByPhone(user)+"";
	}
	
	@ResponseBody
	@RequestMapping(value="/UserRegister",produces="text/json;charset=utf-8")
	public String userRegister(@RequestParam("uName") String uName,@RequestParam("uTel") String uTel,
			@RequestParam("uPwd") String uPwd) {
		User user=new User();
		user.setuName(uName);
		user.setuPwd(uPwd);
		user.setuTel(uTel);
		return userLoginService.addUser(user)+"";
	}
	
}
