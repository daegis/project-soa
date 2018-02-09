package cn.aegisa.project.service;

import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import cn.aegisa.project.vo.customer.CustomerQueryVo;
import cn.aegisa.project.vo.customer.CustomerResponseVo;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/7 16:29
 */
public interface CustomerService {
    void save(CustomerInfo customerInfo) throws Exception;

    LayuiDataGridResponse<CustomerResponseVo> queryList(CustomerQueryVo queryVo);
}
