package cn.person.musicspider.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import cn.person.musicspider.web.vo.SongVo;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.person.musicspider.core.utils.BeanUtils;
import cn.person.musicspider.core.utils.Sequence;
import cn.person.musicspider.core.utils.Sequence.SequenceEnum;
import cn.person.musicspider.dao.SongDAO;
import cn.person.musicspider.mapper.SongMapper;
import cn.person.musicspider.pojo.Song;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SongService;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongDAO songDAO;
	
	@Autowired
	private SongMapper songMapper;
	@Override
	public void addBatchSong(List<SongVo> songs) {
		songDAO.addBatch(songs);
	}
	@Override
	public void updateSong(SongVo song) {
		song.setUpdateTime(new Date());
		Example example = new Example(SongVo.class);
		example.or().andEqualTo("songId",song.getSongId());
		songMapper.updateByExampleSelective(song,example);
	}
	@Override
	public void addSong(SongVo song) {
		song.setId(Sequence.getSequence(SequenceEnum.SONG_SINGERID));
		songMapper.insertSelective(song);
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
		Stream<SongVo> stream = songVos.parallelStream();
		pagination.setItems(songVos);
	}

}
