package cn.person.musicspider.service;

import cn.person.musicspider.pojo.User;
import cn.person.musicspider.result.Pagination;

import java.util.List;

/**
 * Created by lei2j on 2018/5/12.
 */
public interface UserService {

    void addBatchUsers(List<User> userList);

    void addUser(User user);
}
