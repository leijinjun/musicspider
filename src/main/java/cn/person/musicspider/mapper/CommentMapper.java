package cn.person.musicspider.mapper;

import java.util.Map;

import com.github.abel533.mapper.Mapper;

import cn.person.musicspider.pojo.Comment;

public interface CommentMapper extends Mapper<Comment>{

	public void insertComment(Comment comment);

	public Long countByParam(Map<String, Object> params);

	Comment getCommentById(Long commentId);
}
