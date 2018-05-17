package cn.person.musicspider.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.person.musicspider.base.controller.BaseController;

@Controller
public class PageController extends BaseController{

	@RequestMapping("/{page}")
	public String index(@PathVariable("page")String page){
		return page;
	}
	
	@RequestMapping(value="/admin/**/{page}.html",method={RequestMethod.GET})
	public String adminIndex(@PathVariable("page")String page,HttpServletRequest request){
		String requestURI = request.getRequestURI();
		String path = requestURI.replaceFirst(".html", "");
		LOGGER.info("访问页面:",page);
		return path;
	}
}
