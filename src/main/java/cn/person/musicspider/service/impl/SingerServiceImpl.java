package cn.person.musicspider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.person.musicspider.dao.SingerDAO;
import cn.person.musicspider.mapper.SingerMapper;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SingerService;

@Service
@Transactional
public class SingerServiceImpl implements SingerService {

	@Autowired
	private SingerMapper singerMapper;
	@Autowired
	private SingerDAO singerDAO;
	
	@Override
	public void addSinger(Singer singer) {
		singerMapper.insertSelective(singer);
	}
	
	@Override
	public void handleJSON(JSONArray result) {
		if(result==null){
			return;
		}
		List<Singer> singers = new ArrayList<Singer>();
		for (int i = 0,j=result.size(); i < j; i++) {
			JSONObject jo = result.getJSONObject(i);
			Singer singer = new Singer();
			singer.setSingerId(jo.getLong("id"));
			singer.setIsHot(jo.getInteger("topicPerson")==0?true:false);
			singer.setSingerName(jo.getString("name"));
			singer.setPhotoURL(jo.getString("picUrl"));
			singer.setTitle(jo.getString("briefDesc"));
			singers.add(singer);
		}
		singerDAO.addSinger(singers);
	}

	@Override
	public void findSingerList(Pagination<Singer> pagination) {
		Integer offset = pagination.getOffset();
		Integer limit = pagination.getLimit();
		Long total = (long) singerMapper.selectCount(null);
		List<Singer> singers =null;
		if(total!=null&&total>pagination.getOffset()){
			singers = singerMapper.findSingerList(offset,limit);
		}
		pagination.setTotal(total);
		pagination.setItems(singers);
	}

	@Override
	public Singer findSingerById(Long id) {
		return singerMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateSingerById(Singer singer) {
		singerMapper.updateByPrimaryKeySelective(singer);
	}

}
