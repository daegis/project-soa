package cn.aegisa.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 17:11
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public String toLoginPage() {
        return "login/login";
    }
}
