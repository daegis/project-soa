package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.CustomerService;
import cn.aegisa.project.service.JoinService;
import cn.aegisa.project.utils.IDNumberUtil;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import cn.aegisa.project.utils.StrUtil;
import cn.aegisa.project.vo.JoinInfoVo;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void saveJoin(JoinInfoVo infoVo) {
        Integer joinId = infoVo.getId();
        if (joinId == null) {
            // 新增的记录
            JoinInfo joinInfo = new JoinInfo();
            Integer aid = infoVo.getAid();
            Integer cid = infoVo.getCid();
            checkJoinInfo(aid, cid);
            joinInfo.setAid(aid);
            joinInfo.setCid(cid);
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

    private void checkJoinInfo(Integer aid, Integer cid) {
        if (aid == null) {
            throw new RuntimeException("当前没有可以参加的活动");
        }
        if (cid == null) {
            throw new RuntimeException("人员信息异常");
        }
        Integer count = commonService.getBySqlId(JoinInfo.class, "queryCountOfJoin", "aid", aid, "cid", cid);
        if (count > 0) {
            throw new RuntimeException("此人已经参加了此活动，不能再次添加");
        }
    }

    @Override
    public List<String> getCustomerHistory(Integer cid) {
        return commonService.getListBySqlId(CustomerInfo.class, "getHistoryActivity", "cid", cid);
    }

    @Override
    public LayuiDataGridResponse<JoinInfoVo> queryCustomerInActivity(Integer id) {
        LayuiDataGridResponse<JoinInfoVo> response = new LayuiDataGridResponse<>();
        ActivityInfo activityInfo = activityService.getById(id);
        Integer activityPrice = activityInfo.getPrice();
        List<JoinInfo> joinInfoList = commonService.getList(JoinInfo.class, "aid", id);
        // 参加该活动的人员id集合
        List<Integer> customerIdList = joinInfoList.stream().map(JoinInfo::getCid).collect(Collectors.toList());
        List<CustomerInfo> customerInfoList = commonService.getListBySqlId(CustomerInfo.class, "selectByIds", "idList", customerIdList);
        Map<Integer, CustomerInfo> mappingCustomer = mappingCustomerList(customerInfoList);
        List<JoinInfoVo> data = new LinkedList<>();
        for (JoinInfo joinInfo : joinInfoList) {
            // 对应每一条参团信息
            Integer cid = joinInfo.getCid();
            CustomerInfo customerInfo = mappingCustomer.get(cid);
            JoinInfoVo vo = new JoinInfoVo();
            vo.setId(joinInfo.getId());
            vo.setCid(cid);
            vo.setNickname(customerInfo.getNickname());
            vo.setRealName(customerInfo.getRealName());
            LocalDateTime joinDate = joinInfo.getJoinDate();
            if (joinDate != null) {
                vo.setJoinDate(LocalDateTimeUtil.timeToString(joinDate.toLocalDate()));
            }
            String idNumber = customerInfo.getIdNumber();
            vo.setGender(IDNumberUtil.getGender(idNumber));
            vo.setAge(IDNumberUtil.getAgeFromID(idNumber));
            Integer discount = joinInfo.getDiscount();
            Integer prepay = joinInfo.getPrepay();
            int restPay = activityPrice - prepay - discount;
            vo.setDiscount(discount);
            vo.setPrepay(prepay);
            vo.setPayMethod(joinInfo.getPayMethod());
            vo.setRestPay(String.valueOf(restPay));
            vo.setBusSeat(joinInfo.getBusSeat());
            vo.setJoinComment(joinInfo.getJoinComment());
            data.add(vo);
        }
        response.setData(data);
        response.setCount(data.size());
        return response;
    }

    private Map<Integer, CustomerInfo> mappingCustomerList(List<CustomerInfo> customerInfoList) {
        Map<Integer, CustomerInfo> result = new LinkedHashMap<>();
        if (customerInfoList != null) {
            for (CustomerInfo customerInfo : customerInfoList) {
                result.put(customerInfo.getId(), customerInfo);
            }
        }
        return result;
    }
}
