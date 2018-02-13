package cn.aegisa.project.web.controller;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.CustomerService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ActivityService activityService;

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

    @RequestMapping("/userEdit/{id}")
    public String toUserEdit(Model model, @PathVariable Integer id) {
        CustomerInfo customerInfo = customerService.getById(id);
        model.addAttribute("category", "user");
        model.addAttribute("from", "userEdit");
        model.addAttribute("noHead", "no");
        model.addAttribute("customer", customerInfo);
        log.info("传入要修改的人员：{}", JSON.toJSONString(customerInfo));
        return "customer/add";
    }

    @RequestMapping("/activityEdit/{id}")
    public String toActivityEdit(Model model, @PathVariable Integer id) {
        ActivityInfo activityInfo = activityService.getById(id);
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityEdit");
        model.addAttribute("noHead", "no");
        model.addAttribute("activity", activityInfo);
        log.info("传入要修改的活动：{}", JSON.toJSONString(activityInfo));
        return "activity/add";
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
