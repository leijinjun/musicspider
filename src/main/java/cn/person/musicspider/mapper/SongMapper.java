package cn.person.musicspider.mapper;

import java.util.List;

import cn.person.musicspider.pojo.Song;
import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;

public interface SongMapper extends Mapper<Song> {

	 List<Song> findSongsList(@Param("offset")int offset, @Param("limit")int limit);
}
