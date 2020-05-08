package org.turings.near.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.near.entity.CommentDetailBean;
import org.turings.near.entity.Information;
import org.turings.near.entity.Position;
import org.turings.near.entity.Share;
import org.turings.near.service.NearService;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/lyh")
public class NearController {

	@Resource
	private NearService nearService;
	
	@ResponseBody
	@RequestMapping(value="/showComment",produces="text/json;charset=utf-8")
	public String showComment(@RequestParam(value="sId") int sId) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+sId);
		List<CommentDetailBean> list = nearService.showAllComment();
//		String json = JSONArray.fromObject(list).toString();
//		String name = "ssssssssssssss";
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return list.get(1).toString();
	}
	
	//LocationServlet
	@ResponseBody
	@RequestMapping(value="/location",produces="text/json;charset=utf-8")
	public String location(@RequestParam(value="userName") String userName,@RequestParam(value="lat") double lat,@RequestParam(value="lng") double lng) {
		List<Position> posList  = nearService.browseLoc(userName,lat,lng);
		String json = JSONArray.fromObject(posList).toString();
		return json;
	}
	
	//browseInfoServlet
	@ResponseBody
	@RequestMapping(value="/findOneByName",produces="text/json;charset=utf-8")
	public String findOneByName(@RequestParam(value="userName") String name) {
		Information info = nearService.browseInfoByName(name);
		Gson gson = new Gson();
		String json = gson.toJson(info);
		return json;
	}
	
	//browseInformationServlet
	@ResponseBody
	@RequestMapping(value="/findOne",produces="text/json;charset=utf-8")
	public String findOne(@RequestParam(value="lat") double lat,@RequestParam(value="lng") double lng) {
		Information info = nearService.browseInfo(lat,lng);
		Gson gson = new Gson();
		String json = gson.toJson(info);
		return json;
	}

	
//	//browseShareContentServlet
//	@ResponseBody
//	@RequestMapping(value="/browseContent",produces="text/json;charset=utf-8")
//	public String browseContent(@RequestParam(value="title") String title) {
//		Share share = nearService.browseShareContent(title);
//		String json = JSONObject.fromObject(share).toString();
//		return json;
//	}
	
	//broeseShareServlet
	@ResponseBody
	@RequestMapping(value="/browseShareList",produces="text/json;charset=utf-8")
	public String browseShareList(@RequestParam(value="userName") String userName) {
		List<Share> shareList = nearService.browseShare(userName);
		String json = JSONArray.fromObject(shareList).toString();
		return json;
	}
	
	//InsertShareServler
	@ResponseBody
	@RequestMapping(value="/insertShare",produces="text/json;charset=utf-8")
	public int insertShare(@RequestParam(value="userName") String userName,@RequestParam(value="title") String title
			,@RequestParam(value="content") String content,@RequestParam(value="background);") String background) {
		int info = nearService.insertShare(userName, title, content, background);
		return info;
	}
	
}
