package cn.person.musicspider.service.impl;

import cn.person.musicspider.mapper.CommentMapper;
import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Override
	public void addBatchComms(List<Comment> comments) {
		if(comments!=null){
			for (Comment comment : comments) {
				try {
					Comment existComment = getCommentById(comment.getCommentId());
					if(existComment==null){
						comment.setUpdateTime(new Date());
						commentMapper.insertComment(comment);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void insertComment(Comment comment) {
		commentMapper.insertComment(comment);
	}

	@Override
	public Comment getCommentById(Long commentId) {
		return commentMapper.getCommentById(commentId);
	}
}
