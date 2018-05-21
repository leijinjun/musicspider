package cn.person.musicspider.service;

import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;

/**
 * Created by lei2j on 2018/5/20.
 */
public interface SingerService {
    void addSinger(Singer singer);

    void findSingerList(Pagination pagination);

    void updateSinger(Singer singer);
}
