package cn.person.musicspider.service;


import com.alibaba.fastjson.JSONArray;

import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;

public interface SingerService {

	void addSinger(Singer singer);

	void handleJSON(JSONArray result);

	void findSingerList(Pagination<Singer> pagination);

	Singer findSingerById(Long id);

	void updateSingerById(Singer singer);

}
