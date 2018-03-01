package cn.aegisa.project.service;

import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.vo.ActivityAddVo;
import cn.aegisa.project.vo.ActivityResponseVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;

import java.text.ParseException;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/11 9:29
 */
public interface ActivityService {
    void save(ActivityAddVo addVo) throws  Exception;

    LayuiDataGridResponse<ActivityResponseVo> queryList();

    ActivityInfo getById(Integer id);

    List<ActivityInfo> getListCustomerNotIn(Integer id);
}
