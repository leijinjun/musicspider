package cn.person.musicspider.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import cn.person.musicspider.web.vo.SongVo;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.person.musicspider.core.utils.BeanUtils;
import cn.person.musicspider.mapper.SongMapper;
import cn.person.musicspider.pojo.Song;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SongService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongMapper songMapper;

	@Override
	@Transactional
	public void updateSong(SongVo song) {
		song.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		Example example = new Example(SongVo.class);
		example.or().andEqualTo("songId",song.getSongId());
		songMapper.updateByExampleSelective(song,example);
	}
	@Override
	public void addSong(SongVo song) {
		Song existSong = songMapper.selectByPrimaryKey(song.getSongId());
		if(existSong==null){
			songMapper.insertSelective(song);
		}
	}
	@Override
	public SongVo findSongById(Long songId) {
		 Song entity = songMapper.selectByPrimaryKey(songId);
		 return BeanUtils.copyProperties(entity, SongVo.class);
	}
	@Override
	public Long getTotal() {
		return (long) songMapper.selectCount(null);
	}
	@Override
	public void findSongList(Pagination<SongVo> pagination) {
		List<Song> list = songMapper.findSongsList(pagination.getOffset(), pagination.getLimit());
		List<SongVo> songVos = BeanUtils.copyPropertiesList(list, SongVo.class);
		pagination.setItems(songVos);
	}

}
