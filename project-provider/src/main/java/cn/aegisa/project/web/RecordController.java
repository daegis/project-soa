package cn.aegisa.project.web;

import cn.aegisa.project.model.WeightRecord;
import cn.aegisa.project.service.RecordService;
import cn.aegisa.project.vo.ValueResponse;
import cn.aegisa.project.vo.WeightVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian at 2018/3/29 12:52
 */
@RestController
@Slf4j
public class RecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping("/record")
    @ResponseBody
    public Map<String, Object> record(String password, String who, String weight) {
        log.info("传入信息：{}，{}，{}", password, who, weight);
        Map<String, Object> map = new LinkedHashMap<>();
        if (password == null || password.trim().equals("")) {
            map.put("success", false);
            map.put("message", "密钥不能为空Σ(⊙▽⊙");
            return map;
        }
        if (!password.trim().equals("950103")) {
            map.put("success", false);
            map.put("message", "密钥不正确啦￣へ￣");
            return map;
        }
        if (who == null || who.trim().equals("")) {
            map.put("success", false);
            map.put("message", "必须选一个人啦(*^▽^*)");
            return map;
        }
        if (weight == null || weight.trim().equals("")) {
            map.put("success", false);
            map.put("message", "记录值必须填写啦~(*^▽^*)");
            return map;
        }
        try {
            recordService.saveRecord(who, weight);
            map.put("success", true);
        } catch (Exception e) {
            log.error("eee", e);
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public ValueResponse queryData() {
        return recordService.queryData();
    }

    @RequestMapping("/queryInfo")
    @ResponseBody
    public Map<String, Object> queryInfo(String who) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", 1000);
        List<WeightVo> list = recordService.queryList(who);
        map.put("data", list);
        return map;
    }
}
