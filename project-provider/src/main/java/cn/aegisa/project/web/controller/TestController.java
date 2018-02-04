package cn.aegisa.project.web.controller;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.City;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/22 12:26
 */
@Slf4j
@Controller
public class TestController {

    @Autowired
    private ICommonService service;

    @RequestMapping("/01/{id}")
    @ResponseBody
    public String test01(@PathVariable Integer id) {
        List<City> cityList = service.getList(City.class);
        for (City city : cityList) {
            log.info(JSON.toJSONString(city));
        }
        return "" + id;
    }
}
