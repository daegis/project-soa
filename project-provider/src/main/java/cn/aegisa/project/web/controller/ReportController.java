package cn.aegisa.project.web.controller;

import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

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

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/toPage")
    public String toReportPage(Model model) {
        List<ActivityInfo> list = activityService.getAll();
        list.sort((o1, o2) -> o2.getId() - o1.getId());
        model.addAttribute("category", "report");
        model.addAttribute("activities", list);
        return "report/report";
    }
}
