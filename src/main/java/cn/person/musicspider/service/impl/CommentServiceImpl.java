package cn.person.musicspider.service.impl;

import cn.musicspider.dao.sequence.Sequence;
import cn.musicspider.dao.sequence.SequenceUtil;
import cn.person.musicspider.mapper.CommentMapper;
import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.CommentService;
import cn.person.musicspider.web.vo.CommentsVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Override
	public void addBatchComms(List<Comment> comments) {
		if(comments!=null){
			for (Comment comment : comments) {
				try {
					Comment existComment = commentMapper.selectByPrimaryKey(comment.getCommentId());
					if(existComment==null){
						comment.setUpdateTime(new Date());
						commentMapper.insertSelective(comment);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void findCommentPage(Pagination<CommentsVo> page) {
		Map<String, Object> params = page.getParams();
		Integer pageNum = page.getPageNum();
		Integer limit = page.getLimit();
	    Long total = commentMapper.countByParam(params);
	    if(page.getOffset()<total){
			PageHelper.startPage(pageNum, limit, false);
			List<CommentsVo> list = commentMapper.findCommentsList(params);
			page.setItems(list);
			page.setTotal(total);
		}
	}

}
