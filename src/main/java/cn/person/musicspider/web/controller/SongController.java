package cn.person.musicspider.web.controller;

import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.service.SingerService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//以restful风格输出数据,没有视图
//@RestController
@RequestMapping("/artist")
public class SongController extends BaseController {

	@Autowired
	private SingerService singerService;
	@Autowired
	private SongService songService;

	@RequestMapping(value = "",method = {RequestMethod.GET})
	public ModelAndView getSongPage(Pagination page, ModelAndView mv){
		mv.setViewName("music");
		songService.findSongList(page);
		mv.addObject("songs",page.getItems());
		mv.addObject("page",page);
		return mv;
	}
	
	@RequestMapping(value="/{id}")
	public String getSong(@PathVariable("id")Long id,Model model){
		LOGGER.info("params:---->id:{}", id);
		Singer singer = singerService.findSingerById(id);
		//Response result = getResult(Jsoup.connect(singer.getSingerURL()), Method.GET);
		model.addAttribute("artist", singer);
		return "song";
	}
	
}
