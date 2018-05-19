package cn.person.musicspider.service.impl;

import cn.person.musicspider.mapper.UserMapper;
import cn.person.musicspider.pojo.User;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by lei2j on 2018/5/12.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addBatchUsers(List<User> userList) {
        if(userList!=null){
            for (User user : userList) {
                try {
                    User existUser = userMapper.selectByPrimaryKey(user.getUserId());
                    if(existUser!=null){
                        continue;
                    }
                    user.setUpdateTime(new Date());
                    userMapper.insertSelective(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addUser(User user) {
        user.setUpdate_time(new Timestamp(System.currentTimeMillis()));
        User existUser = userMapper.selectByPrimaryKey(user.getUserId());
        if(existUser==null){
            userMapper.insertSelective(user);
        }
    }

}
