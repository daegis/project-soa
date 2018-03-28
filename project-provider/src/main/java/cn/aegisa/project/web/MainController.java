package cn.aegisa.project.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/28 23:38
 */

@Controller
@Slf4j
public class MainController {

    @RequestMapping("/main")
    public String toMainPage(Model model) {
        model.addAttribute("name", "测试名称");
        return "main";
    }
}
