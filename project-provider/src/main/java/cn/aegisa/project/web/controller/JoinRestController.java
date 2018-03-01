package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.vo.JoinInfoVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("/add")
    public String joinAction(JoinInfoVo infoVo) {
        log.info("前台传入参加活动信息：" + JSON.toJSONString(infoVo));

        return MessageResponse.success();
    }
}
