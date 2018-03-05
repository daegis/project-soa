package cn.aegisa.project.web.controller;

import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.ReportService;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
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

    @Autowired
    private ReportService reportService;

    @RequestMapping("/toPage")
    public String toReportPage(Model model) {
        List<ActivityInfo> list = activityService.getAll();
        list.sort((o1, o2) -> o2.getId() - o1.getId());
        model.addAttribute("category", "report");
        model.addAttribute("activities", list);
        return "report/report";
    }

    @RequestMapping("/commonInfo/{aid}")
    public void reportCommonTable(@PathVariable Integer aid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        String realPath = servletContext.getRealPath(File.separator);
        String filePath = realPath + File.separator + "template" + File.separator + "infoTable.xls";
        FileInputStream in = new FileInputStream(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("content-disposition", "attachment;fileName=mingdan" + System.currentTimeMillis() + ".xls");
        reportService.reportCommonTable(aid, in, outputStream);
    }

}
