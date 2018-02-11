package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.vo.ActivityAddVo;
import cn.aegisa.project.vo.ActivityResponseVo;
import cn.aegisa.project.vo.CustomerResponseVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import com.alibaba.fastjson.JSON;
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
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityRestController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/add")
    public String activityAdd(ActivityAddVo addVo) {
        log.info("前台传入活动信息:{}", JSON.toJSONString(addVo));
        try {
            activityService.save(addVo);
            return MessageResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return MessageResponse.fail(e.getMessage());
        }
    }

    @RequestMapping("/findOnPage")
    public String activityListShow() {
        LayuiDataGridResponse<ActivityResponseVo> response = activityService.queryList();
        return JSON.toJSONString(response);
    }
}
