package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.service.CustomerService;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/7 15:33
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

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
}
