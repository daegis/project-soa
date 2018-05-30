package cn.aegisa.project.web.controller;

import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.ReportService;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
//    @SystemLog(message = "必须是奥特曼才能用", type = 1)
    public String toReportPage(Model model) {
        List<ActivityInfo> list = activityService.getAll();
        list.sort((o1, o2) -> o2.getId() - o1.getId());
        model.addAttribute("category", "report");
        model.addAttribute("activities", list);
        return "report/report";
    }

    @RequestMapping("/announce/{aid}")
    public String toReportPage(Model model, @PathVariable Integer aid) {
        int pageCount = 15;
        ActivityInfo activityInfo = activityService.getById(aid);
        // 返回格式 name：xxx  id：zzz
        List<List<Map<String, String>>> toPageList = new LinkedList<>();
        List<Map<String, String>> list = reportService.getAnnounceCustomers(aid);
        int size = list.size();
        int page = size / pageCount;
        if (size % pageCount != 0) {
            page++;
        }
        for (int i = 0; i < page; i++) {
            List<Map<String, String>> innerList = new LinkedList<>();
            toPageList.add(innerList);
        }
        // 将返回list进行分组
        for (Map<String, String> customer : list) {
            String number = customer.get("number");
            int intNumber = Integer.parseInt(number) - 1;
            int listIndex = intNumber / pageCount;
            List<Map<String, String>> maps = toPageList.get(listIndex);
            maps.add(customer);
        }
        model.addAttribute("name", activityInfo.getActivityName());
        model.addAttribute("time", LocalDateTimeUtil.timeToString(activityInfo.getActivityDate().toLocalDate()));
        model.addAttribute("pageList", toPageList);
        return "report/announce";
    }

    @RequestMapping("/commonInfo/{aid}/{type}")
    public void reportCommonTable(@PathVariable Integer aid, @PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        String realPath = servletContext.getRealPath(File.separator);
        String filePath = realPath + File.separator + "template" + File.separator + type + ".xls";
        FileInputStream in = new FileInputStream(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("content-disposition", "attachment;fileName=" + type + System.currentTimeMillis() + ".xls");
        reportService.reportCommonTable(aid, type, in, outputStream);
    }

    @RequestMapping("/insurance/{aid}")
    public void reportInsurance(@PathVariable Integer aid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        String realPath = servletContext.getRealPath(File.separator);
        String filePath = realPath + File.separator + "template" + File.separator + "insurance.xls";
        FileInputStream in = new FileInputStream(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("content-disposition", "attachment;fileName=baoxian" + System.currentTimeMillis() + ".xls");
        reportService.reportInsurance(aid, in, outputStream);
    }

}
