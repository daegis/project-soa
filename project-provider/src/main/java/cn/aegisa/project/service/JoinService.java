package cn.aegisa.project.service;

import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.vo.JoinInfoVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:42
 */
public interface JoinService {
    void saveJoin(JoinInfoVo infoVo);

    List<String> getCustomerHistory(Integer cid);

    LayuiDataGridResponse<JoinInfoVo> queryCustomerInActivity(Integer id);

    void setBusSeat(Integer id, Integer seat);

    void deleteFromActivity(Integer id);

    JoinInfo getById(Integer id);
}
