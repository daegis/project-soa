package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.utils.StrUtil;
import cn.aegisa.project.vo.ActivityAddVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        ActivityInfo info = new ActivityInfo();
        info.setActivityName(addVo.getName());
        info.setCreateTime(new Date());
        info.setDayCount(addVo.getDayCount());
        info.setPrice(addVo.getPrice());
        String date = addVo.getDate();
        Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        info.setActivityDate(dateObj);
        info.setCreateTime(new Date());
        commonService.save(info);
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
