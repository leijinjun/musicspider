package cn.person.musicspider.service;

import java.util.List;

import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.web.vo.CommentsVo;

public interface CommentService {

	/**
	 * @param comments
	 */
	void addBatchComms(List<Comment> comments);
	
	void findCommentPage(Pagination<CommentsVo> page);

}
