package musicspider;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.person.musicspider.core.utils.Sequence;
import cn.person.musicspider.mapper.SingerMapper;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SingerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext*.xml")
public class SingerCase {

	@Autowired
	private SingerService singerService;
	@Autowired
	private SingerMapper singerMapper;
	@Test
	public void test1(){
		
		Pagination<Singer> pagination=new Pagination<Singer>();
		pagination.setPageNum(1);
		pagination.setLimit(30);
		singerService.findSingerList(pagination);
		System.out.println(pagination);
//		System.out.println(singerMapper.selectCount(null));
	}
	
	@Test
	public void test2(){
		Pagination<Singer> pagination = new Pagination<Singer>();
		pagination.setPageNum(1);
		pagination.setLimit(30);
		singerService.findSingerList(pagination);
		List<Singer> items = pagination.getItems();
		while(items!=null&&items.size()>0){
			for (Singer singer : items) {
				Singer s = new Singer();
				s.setSingerId(singer.getSingerId());
//				s.setSingerURL(singer.getSingerURL().replaceAll("#/", ""));
				singerMapper.updateByPrimaryKeySelective(s);
			}
			pagination.setPageNum(pagination.getPageNum()+1);
			singerService.findSingerList(pagination);
			items = pagination.getItems();
		}
	}
	
	@Test
	public void test3(){
		for (int j = 0; j < 50; j++) {
			new Thread(){
				@Override
				public void run(){
					System.out.println(Sequence.getSequence(Sequence.SequenceEnum.SONG_ALBUMID));
				}
			}.start();
		}
		
		while(true){
			
		}
	}
}
