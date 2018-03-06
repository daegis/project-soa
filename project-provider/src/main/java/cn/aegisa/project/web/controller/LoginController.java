package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.model.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 17:11
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public String toLoginPage() {
        return "login/login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(User user) {
        log.info("尝试登录的用户：" + JSON.toJSONString(user));
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            log.info("登录成功");
            return MessageResponse.success();
        } catch (UnknownAccountException e) {
            log.info("密码错误，登录失败");
            return MessageResponse.fail("用户名或密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            return MessageResponse.fail(e.getMessage());
        }
    }
}
