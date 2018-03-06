package cn.aegisa.project.service;

import cn.aegisa.project.model.User;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/6 9:58
 */
public interface UserService {
    User findByUsernameAndPassword(String username, String password);
}
