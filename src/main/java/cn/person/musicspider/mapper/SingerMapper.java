package cn.person.musicspider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;

import cn.person.musicspider.pojo.Singer;

public interface SingerMapper extends Mapper<Singer> {

	void insertSinger(Singer singer);

	List<Singer> findSingerList(@Param("offset")Integer offset, @Param("limit")Integer limit);

}
