package cn.aegisa.project.service;

import cn.aegisa.project.vo.ValueResponse;
import cn.aegisa.project.vo.WeightVo;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian at 2018/3/29 12:53
 */
public interface RecordService {
    void saveRecord(String who, String weight);

    ValueResponse queryData();

    List<WeightVo> queryList(String name);
}
