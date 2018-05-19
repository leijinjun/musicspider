package cn.person.musicspider.service;

import java.util.List;

import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.web.vo.SongVo;

public interface SongService {

	void updateSong(SongVo song);

	void addSong(SongVo song);

	SongVo findSongById(Long songId);

	Long getTotal();

	void findSongList(Pagination<SongVo> pagination);

}
