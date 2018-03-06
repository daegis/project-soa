package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.User;
import cn.aegisa.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/6 9:58
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private ICommonService commonService;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return commonService.get(User.class, "name", username, "password", password);
    }
}
