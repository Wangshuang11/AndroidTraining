package org.turings.index.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.turings.index.entity.Comment;

public interface CommentMapper6 {
	public List<Comment> findComments();
	public int insertCommentFromId(@Param("id")int id,@Param("comm")Comment comm);
	public int updateComment(String starnum,int id);
}
