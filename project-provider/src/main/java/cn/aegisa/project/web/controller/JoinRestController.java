package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.service.JoinService;
import cn.aegisa.project.vo.JoinInfoVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:28
 */
@RestController
@RequestMapping("/join")
@Slf4j
public class JoinRestController {

    @Autowired
    private JoinService joinService;

    @RequestMapping("/add")
    public String joinAction(JoinInfoVo infoVo) {
        log.info("前台传入参加活动信息：" + JSON.toJSONString(infoVo));
        try {
            joinService.saveJoin(infoVo);
            return MessageResponse.success();
        } catch (Exception e) {
            log.error("保存行程的时候出现了异常", e);
            return MessageResponse.fail(e.getMessage());
        }
    }
}
