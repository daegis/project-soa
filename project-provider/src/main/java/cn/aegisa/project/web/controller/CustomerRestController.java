package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.service.CustomerService;
import cn.aegisa.project.service.JoinService;
import cn.aegisa.project.vo.CustomerQueryVo;
import cn.aegisa.project.vo.CustomerResponseVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/7 15:33
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JoinService joinService;

    @RequestMapping("/add")
    public String customerAdd(CustomerInfo customerInfo) {
        log.info("前台传入人员信息:{}", JSON.toJSONString(customerInfo));
        try {
            customerService.save(customerInfo);
            return MessageResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return MessageResponse.fail(e.getMessage());
        }
    }

    @RequestMapping("/findOnPage")
    public String customerListShow(CustomerQueryVo queryVo) {
        log.info("传入的列表查询信息:{}", JSON.toJSONString(queryVo));
        LayuiDataGridResponse<CustomerResponseVo> response = customerService.queryList(queryVo);
        return JSON.toJSONString(response);
    }

    @RequestMapping("/showHistory")
    public String showCustomerHistory(Integer cid) {
        List<String> activityNames = joinService.getCustomerHistory(cid);
        StringBuilder sb = new StringBuilder();
        int count = 1;
        if (activityNames != null && activityNames.size() > 0) {
            sb.append("客户参加的活动：").append("<br>");
            Iterator<String> iterator = activityNames.iterator();
            String first = iterator.next();
            sb.append(String.valueOf(count)).append("、").append(first);
            while (iterator.hasNext()) {
                String next = iterator.next();
                count++;
                sb.append("<br><hr>").append(String.valueOf(count)).append("、").append(next);
            }
        } else {
            sb.append("该客户未参加任何活动");
        }
        return MessageResponse.success(sb.toString());
    }
}
