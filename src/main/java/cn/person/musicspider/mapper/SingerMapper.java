package cn.person.musicspider.mapper;

import cn.person.musicspider.pojo.Singer;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lei2j on 2018/5/20.
 */
public interface SingerMapper extends Mapper<Singer>{
    List<Singer> find(@Param("offset") Integer offset,@Param("limit") Integer limit);
}
