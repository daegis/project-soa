package cn.aegisa.project.web.controller;

import cn.aegisa.project.common.MessageResponse;
import cn.aegisa.project.service.JoinService;
import cn.aegisa.project.vo.JoinInfoVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:28
 */
@SuppressWarnings("Duplicates")
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

    @RequestMapping("/customers/{id}")
    public String queryCustomersInActivity(@PathVariable Integer id) {
        LayuiDataGridResponse<JoinInfoVo> result = joinService.queryCustomerInActivity(id);
        return JSON.toJSONString(result);
    }

    // 设置汽车座位号
    @RequestMapping("/setBusSeat")
    public String setBusSeat(Integer id, String seat) {
        return setInActivityInfo("bus", id, seat);
    }

    // 设置用户就餐座位号
    @RequestMapping("/setTableSeat")
    public String setTableSeat(Integer id, String seat) {
        return setInActivityInfo("table", id, seat);
    }

    // 设置用户房间号
    @RequestMapping("/setRoomId")
    public String setRoomId(Integer id, String seat) {
        return setInActivityInfo("room", id, seat);
    }

    private String setInActivityInfo(String type, Integer id, String number) {
        try {
            int seatNumber = Integer.parseInt(number);
            joinService.setInActivityInfo(type, id, seatNumber);
            return MessageResponse.success();
        } catch (Exception e) {
            return MessageResponse.fail(e.getMessage());
        }
    }

    @RequestMapping("/deleteFromActivity")
    public String deleteFromActivity(Integer id) {
        try {
            joinService.deleteFromActivity(id);
            return MessageResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return MessageResponse.fail(e.getMessage());
        }
    }
}
