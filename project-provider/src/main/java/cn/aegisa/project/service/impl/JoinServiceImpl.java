package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.service.JoinService;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import cn.aegisa.project.utils.StrUtil;
import cn.aegisa.project.vo.JoinInfoVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:43
 */
@Service
@Slf4j
public class JoinServiceImpl implements JoinService {

    @Autowired
    private ICommonService commonService;

    @Override
    public void saveJoin(JoinInfoVo infoVo) {
        Integer joinId = infoVo.getId();
        if (joinId == null) {
            // 新增的记录
            JoinInfo joinInfo = new JoinInfo();
            joinInfo.setAid(infoVo.getAid());
            joinInfo.setCid(infoVo.getCid());
            joinInfo.setJoinComment(infoVo.getJoinComment());
            String payMethod = infoVo.getPayMethod();
            if (StrUtil.strCheckNotNull(payMethod)) {
                joinInfo.setPayMethod(payMethod);
            } else {
                joinInfo.setPayMethod("未付款");
            }
            Integer discount = infoVo.getDiscount();
            if (discount == null) {
                joinInfo.setDiscount(0);
            } else {
                joinInfo.setDiscount(discount);
            }
            Integer prepay = infoVo.getPrepay();
            if (prepay == null) {
                joinInfo.setPrepay(0);
            } else {
                joinInfo.setPrepay(prepay);
            }
            joinInfo.setLastModifyTime(LocalDateTime.now());
            String joinDate = infoVo.getJoinDate();
            if (StrUtil.strCheckNotNull(joinDate)) {
                joinInfo.setJoinDate(LocalDateTimeUtil.fromString(joinDate));
            } else {
                joinInfo.setJoinDate(LocalDateTime.now());
            }
            commonService.save(joinInfo);
        } else {
            // 修改的记录

        }
    }

    @Override
    public List<String> getCustomerHistory(Integer cid) {
        return commonService.getListBySqlId(CustomerInfo.class, "getHistoryActivity", "cid", cid);
    }
}
