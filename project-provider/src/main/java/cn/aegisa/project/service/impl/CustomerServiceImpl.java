package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.service.CustomerService;
import cn.aegisa.project.utils.IDNumberUtil;
import cn.aegisa.project.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/7 16:29
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ICommonService commonService;

    @Override
    public void save(CustomerInfo customerInfo) throws Exception {
        paramsCheck(customerInfo);
        customerInfo.setLastModifyTime(new Date());
        commonService.save(customerInfo);
        log.info("成功保存了人员信息");
    }

    private void paramsCheck(CustomerInfo customerInfo) throws Exception {
        if (!StrUtil.strCheckNotNull(customerInfo.getNickname())) {
            throw new Exception("昵称不能为空");
        }
        String telephone = customerInfo.getTelephone();
        if (!StrUtil.strCheckNotNull(telephone)) {
            throw new Exception("电话号码不能为空");
        }
        if (!telephone.matches("1[3-9]\\d{9}")) {
            throw new Exception("电话号码不符合规则");
        }
        if (StrUtil.strCheckNotNull(customerInfo.getIdNumber())) {
            if (!IDNumberUtil.checkID(customerInfo.getIdNumber())) {
                throw new Exception("身份证号码不符合规则, 未知请留空");
            }
        }
    }
}
