package cn.aegisa.project.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 12:16
 */
@Controller
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @RequestMapping("/toPage")
    public String toReportPage(Model model) {
        model.addAttribute("category", "report");
        return "report/report";
    }
}
