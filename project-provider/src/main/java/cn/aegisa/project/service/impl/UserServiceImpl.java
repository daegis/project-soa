package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.User;
import cn.aegisa.project.service.UserService;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import cn.aegisa.project.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        if (!StrUtil.strCheckNotNull(username, password)) {
            throw new AuthenticationException("用户名或密码不能为空");
        }
        User user = commonService.get(User.class, "name", username);
        if (user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if (user.getStatus() != 0) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lockTime = user.getLockTime();
            if (!now.isAfter(lockTime)) {
                throw new AuthenticationException("因错误次数超限，用户已被锁定。这个锁定将在" + LocalDateTimeUtil.timeToString(lockTime) + "解锁。" +
                        "如需提前解锁请联系管理员。");
            }
        }
        String dbPassword = user.getPassword();
        if (dbPassword.equals(password)) {
            // 密码正确 将错误次数设为0
            user.setWrongTime(0);
            user.setStatus(0);
            commonService.update(user);
            return user;
        }
        // 密码不正确
        Integer wrongTime = user.getWrongTime();
        if (wrongTime <= 5) {
            user.setWrongTime(wrongTime + 1);
        } else {
            // 错误次数太多了
            user.setWrongTime(0);
            user.setStatus(1);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime _12hLater = now.plusHours(2);
            user.setLockTime(_12hLater);
        }
        commonService.update(user);
        return null;
    }
}
