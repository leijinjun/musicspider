package cn.person.musicspider.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;

import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.web.vo.CommentsVo;

public interface CommentMapper extends Mapper<Comment>{

	public List<CommentsVo> findCommentsList(Map<String, Object> params);

	public void insertComment(Comment comment);

	public Long countByParam(Map<String, Object> params);
}
