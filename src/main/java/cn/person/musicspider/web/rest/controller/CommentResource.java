package cn.person.musicspider.web.rest.controller;

import java.util.HashMap;
import java.util.Map;

import cn.person.musicspider.result.Response;
import cn.person.musicspider.result.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.CommentService;
import cn.person.musicspider.web.vo.CommentsVo;

@RestController("commentResource")
@RequestMapping("/api/song/comment")
public class CommentResource extends BaseController{
	
	@Autowired
	private CommentService commentService;

	@RequestMapping(value="")
	public Response getCommentPageList(Pagination pagination){
		Map<String, Object> params = new HashMap<>();
		pagination.setParams(params);
		commentService.findCommentPage(pagination);
		return new Response(ResponseCode.OK,pagination);
	}

}
