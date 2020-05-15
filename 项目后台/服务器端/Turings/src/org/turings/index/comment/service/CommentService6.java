package org.turings.index.comment.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.index.comment.dao.CommentMapper6;
import org.turings.index.entity.Comment;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false)
public class CommentService6 {
	@Resource
	private CommentMapper6 commentMapper6;
	@Transactional(readOnly=true)
	public List<Comment> findComments(){
		return this.commentMapper6.findComments();
	}
	
	public int insertCommentFromId(int id,Comment comm) {
		return this.commentMapper6.insertCommentFromId(id, comm);
	}
	public int updateComment(String starnum,int id) {
		return this.commentMapper6.updateComment(starnum, id);
	}
	public String toJsonArray(List<Comment> list) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", list.get(i).getId());
			obj.put("icon", list.get(i).getIcon());
			obj.put("name", list.get(i).getName());
			obj.put("num", list.get(i).getNum());
			obj.put("content", list.get(i).getContent());
			obj.put("djIcon", list.get(i).getDjIcon());
			obj.put("djName", list.get(i).getDjName());
			obj.put("time", list.get(i).getTime());
			array.add(obj);
		}
		JSONObject objt = new JSONObject();
		objt.put("list", array);
		return objt.toString();
	}
}
