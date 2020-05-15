package org.turings.index.comment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turings.index.comment.service.CommentService6;
import org.turings.index.entity.Comment;

@Controller
@RequestMapping("/lph")
public class CommentController6 {
	@Resource
	private CommentService6 commentService6;

	@ResponseBody
	@RequestMapping(value = "/findComments", produces = "text/json;charset=utf-8")
	public String findComments() {
		List<Comment> comments = this.commentService6.findComments();
		String string = this.commentService6.toJsonArray(comments);
		System.out.println("comment+***");
		System.out.println(string);
		return string;
	}

	@ResponseBody
	@RequestMapping(value = "/insertComment")
	public String insertCommentFromId(@RequestParam(value = "id") int id, @RequestParam("content") String content) {
		Comment comment = new Comment();
		String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		comment.setContent(content);
		comment.setTime(time);
		int n = this.commentService6.insertCommentFromId(id, comment);
		return n + "";
	}

	@ResponseBody
	@RequestMapping("/updateComment")
	public String updateComment(@RequestParam(value = "starnum") String starnum, @RequestParam(value = "id") int id) {

		int n = this.commentService6.updateComment(starnum, id);
		if (n >= 1) {
			return "lph_one";
		} else {
			return "null";
		}

	}
}
