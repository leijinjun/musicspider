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
                    User existUser = userMapper.getUserById(user.getUserId());
                    if(existUser!=null){
                        continue;
                    }
                    user.setUpdateTime(new Date());
                    userMapper.insertUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addUser(User user) {
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        User existUser = userMapper.getUserById(user.getUserId());
        if(existUser==null){
            userMapper.insertUser(user);
        }
    }

    @Override
    public void findUserList(Pagination pagination) {
        List<User> userList = userMapper.find(pagination.getOffset(),pagination.getLimit());
        pagination.setItems(userList);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUserById(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }
}
