package cn.person.musicspider.service.impl;

import cn.person.musicspider.mapper.SingerMapper;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lei2j on 2018/5/20.
 */
@Service
public class SingerServiceImpl implements SingerService{

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public void addSinger(Singer singer) {
        singer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        singerMapper.insertSelective(singer);
    }

    @Override
    public void findSingerList(Pagination pagination) {
        List<Singer> singerList = singerMapper.find(pagination.getOffset(),pagination.getLimit());
        pagination.setItems(singerList);
    }

    @Override
    public void updateSinger(Singer singer) {
        singerMapper.updateByPrimaryKey(singer);
    }
}
