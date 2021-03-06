package org.turings.index.pre.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.index.entity.Pre;
import org.turings.index.pre.dao.PreMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=true)
public class PreService {
	@Resource
	private PreMapper preMapper;
	public List<Pre> findPreAll(){
		return this.preMapper.findPreAll();
	}
	@Transactional(readOnly=false)
	public int updatePre(int id,String num) {
		return this.preMapper.updatePre(id, num);
	}
	public String toJsonArray(List<Pre> list) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", list.get(i).getId());
			obj.put("title", list.get(i).getTitle());
			obj.put("num", list.get(i).getNum());
			obj.put("content", list.get(i).getContent());
			obj.put("author", list.get(i).getAuthor());
			array.add(obj);
		}
		JSONObject objt = new JSONObject();
		objt.put("list", array);
		return objt.toString();
	}
}
