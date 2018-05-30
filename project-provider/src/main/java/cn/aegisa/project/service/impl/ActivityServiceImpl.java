package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import cn.aegisa.project.utils.StrUtil;
import cn.aegisa.project.vo.ActivityAddVo;
import cn.aegisa.project.vo.ActivityResponseVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/11 9:29
 */
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ICommonService commonService;

    @Override
    public void save(ActivityAddVo addVo) throws Exception {
        paramsCheck(addVo);
        Integer id = addVo.getId();
        ActivityInfo info = new ActivityInfo();
        info.setActivityName(addVo.getName());
        info.setCreateTime(LocalDateTime.now());
        info.setDayCount(addVo.getDayCount());
        info.setPrice(addVo.getPrice());
        String date = addVo.getDate();
        LocalDateTime dateObj = LocalDateTimeUtil.fromString(date);
        info.setActivityDate(dateObj);
        if (id == null) {
            log.info("存入新的活动记录");
            commonService.save(info);
        } else {
            log.info("修改活动记录");
            info.setId(id);
            commonService.update(info);
        }
    }

    @Override
    public LayuiDataGridResponse<ActivityResponseVo> queryList() {
        LayuiDataGridResponse<ActivityResponseVo> response = new LayuiDataGridResponse<>();
        List<ActivityResponseVo> voList = new LinkedList<>();
        List<ActivityInfo> list = commonService.getList(ActivityInfo.class);
        for (ActivityInfo info : list) {
            ActivityResponseVo vo = new ActivityResponseVo();
            vo.setId(String.valueOf(info.getId()));
            vo.setDay(String.valueOf(info.getDayCount()));
            vo.setName(info.getActivityName());
            vo.setPrice(String.valueOf(info.getPrice()));
            LocalDate activityDate = info.getActivityDate().toLocalDate();
            String dateStr = "--";
            if (activityDate != null) {
                dateStr = LocalDateTimeUtil.timeToString(activityDate);
            }
            vo.setDate(dateStr);
            // 查询当前活动的人数
            Integer customerCount = commonService.getBySqlId(JoinInfo.class, "queryCustomerCountInActivity", "aid", info.getId());
            if (customerCount != null) {
                vo.setCurrentCount(String.valueOf(customerCount));
            } else {
                vo.setCurrentCount("--");
            }
            Integer isDone = info.getIsDone();
            if (isDone == 0) {
                vo.setStatus("<font color=\"green\">进行中</font>");
            } else {
                vo.setStatus("<font color=\"red\">已完成</font>");
            }
            voList.add(vo);
        }
        Collections.reverse(voList);
        response.setData(voList);
        return response;
    }

    @Override
    public ActivityInfo getById(Integer id) {
        return commonService.get(id, ActivityInfo.class);
    }

    @Override
    public List<ActivityInfo> getListCustomerNotIn(Integer id) {
        return commonService.getListBySqlId(ActivityInfo.class, "selectWhereCustomerNotIn", "cid", id);
    }

    @Override
    public Integer queryCustomerCount(Integer id) {
        return commonService.getBySqlId(JoinInfo.class, "queryCustomerCountInActivity", "aid", id);
    }

    @Override
    public List<ActivityInfo> getAll() {
        return commonService.getList(ActivityInfo.class);
    }

    @Override
    public void endActivity(Integer aid) {
        ActivityInfo info = commonService.get(aid, ActivityInfo.class);
        Integer isDone = info.getIsDone();
        if (isDone == 0) {
            info.setIsDone(1);
        } else if (isDone == 1) {
            info.setIsDone(0);
        }
        commonService.update(info);
    }

    @Override
    public String getConflictSeat(Integer id) {
        List<JoinInfo> infoList = commonService.getList(JoinInfo.class, "aid", id);
        if (infoList != null && infoList.size() > 0) {
            int nullCount = 0;
            Map<Integer, Integer> map = new ConcurrentHashMap<>();
            for (JoinInfo joinInfo : infoList) {
                Integer busSeat = joinInfo.getBusSeat();
                if (busSeat == null) {
                    nullCount++;
                    continue;
                }
                Integer integer = map.get(busSeat);
                if (integer == null) {
                    map.put(busSeat, 1);
                } else {
                    map.put(busSeat, integer + 1);
                }
            }
            Set<Integer> set = new TreeSet<>();
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                if (value > 1) {
                    set.add(key);
                }
            }
            if (set.isEmpty() && nullCount == 0) {
                return null;
            }
            StringBuilder sb = new StringBuilder("发现一些关于此活动的异常信息：<br>");
            if (nullCount > 0) {
                sb.append("有<font color=\"red\">【").append(nullCount).append("】</font>个参与活动的客户还没有设置座位号。<br>");
            }
            if (!set.isEmpty()) {
                Iterator<Integer> iterator = set.iterator();
                Integer next = iterator.next();
                StringBuilder numbers = new StringBuilder(String.valueOf(next));
                while (iterator.hasNext()) {
                    Integer in = iterator.next();
                    numbers.append("，").append(String.valueOf(in));
                }
                sb.append("检测到重复的座位号（严重）：").append("<font color=\"red\">").append(numbers).append("</font>。");
            }
            return sb.toString();
        }
        return null;
    }

    private void paramsCheck(ActivityAddVo addVo) throws Exception {
        String date = addVo.getDate();
        if (!StrUtil.strCheckNotNull(date)) {
            throw new Exception("日期不能为空");
        }
        String name = addVo.getName();
        if (!StrUtil.strCheckNotNull(name)) {
            throw new Exception("活动名称不能为空");
        }
        Integer dayCount = addVo.getDayCount();
        if (dayCount == null) {
            throw new Exception("天数不能为空");
        }
        Integer price = addVo.getPrice();
        if (price == null) {
            throw new Exception("价格不能为空");
        }
    }
}
