package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.utils.StrUtil;
import cn.aegisa.project.vo.ActivityAddVo;
import cn.aegisa.project.vo.ActivityResponseVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
        info.setCreateTime(new Date());
        info.setDayCount(addVo.getDayCount());
        info.setPrice(addVo.getPrice());
        String date = addVo.getDate();
        Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        info.setActivityDate(dateObj);
        info.setCreateTime(new Date());
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
            Date date = info.getActivityDate();
            String dateStr = "--";
            if (date != null) {
                dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            vo.setDate(dateStr);
            vo.setCurrentCount("--");
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
