package cn.aegisa.project.service;

import cn.aegisa.project.vo.ValueResponse;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian at 2018/3/29 12:53
 */
public interface RecordService {
    void saveRecord(String who, String weight);

    ValueResponse queryData();
}
