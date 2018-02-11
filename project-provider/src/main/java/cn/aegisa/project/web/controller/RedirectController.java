package cn.aegisa.project.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/22 12:26
 */
@Slf4j
@Controller
@RequestMapping("/to")
public class RedirectController {

    @RequestMapping("/main")
    public String toWelcomePage() {
        return "main/test";
    }

    @RequestMapping("/userAdd")
    public String toUserAdd(Model model) {
        model.addAttribute("category", "user");
        model.addAttribute("from", "userAdd");
        return "customer/add";
    }

    @RequestMapping("/userList")
    public String toUserList(Model model) {
        model.addAttribute("category", "user");
        model.addAttribute("from", "userList");
        return "customer/list";
    }

    @RequestMapping("/activityAdd")
    public String toActivityAdd(Model model) {
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityAdd");
        return "activity/add";
    }

    @RequestMapping("/activityList")
    public String toActivityList(Model model) {
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityList");
        return "activity/list";
    }
}
