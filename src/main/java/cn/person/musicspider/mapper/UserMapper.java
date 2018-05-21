package cn.person.musicspider.mapper;

import cn.person.musicspider.pojo.User;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lei2j on 2018/5/12.
 */
public interface UserMapper extends Mapper<User>{

    List<User> find(@Param("offset") Integer offset,@Param("limit") Integer limit);

    User getUserById(Long userId);

    void insertUser(User user);

    void updateUserById(User user);
}
