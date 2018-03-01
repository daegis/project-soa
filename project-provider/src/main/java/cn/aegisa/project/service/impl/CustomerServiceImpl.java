package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.exception.DuplicatedIdNumberException;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.service.CustomerService;
import cn.aegisa.project.utils.IDNumberUtil;
import cn.aegisa.project.utils.LocalDateTimeUtil;
import cn.aegisa.project.utils.StrUtil;
import cn.aegisa.project.vo.LayuiDataGridResponse;
import cn.aegisa.project.vo.CustomerQueryVo;
import cn.aegisa.project.vo.CustomerResponseVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        Integer id = customerInfo.getId();
        try {
            paramsCheck(customerInfo);
        } catch (DuplicatedIdNumberException e) {
            if (id == null) {
                // 新增记录，不允许重复
                throw e;
            }
        }
        customerInfo.setLastModifyTime(LocalDateTime.now());
        if (id == null) {
            log.info("新增人员信息");
            commonService.save(customerInfo);
        } else {
            log.info("更新人员信息");
            commonService.update(customerInfo);
        }
    }

    @Override
    public LayuiDataGridResponse<CustomerResponseVo> queryList(CustomerQueryVo queryVo) {
        LayuiDataGridResponse<CustomerResponseVo> response = new LayuiDataGridResponse<>();
        Map<String, Object> params = new LinkedHashMap<>();
        Integer page = queryVo.getPage();
        if (page == null) {
            page = 1;
        }
        Integer limit = queryVo.getLimit();
        if (limit == null) {
            limit = 10;
        }
        Integer start = limit * (page - 1);
        params.put("start", start);
        params.put("limit", limit);
        params.put("desc", "desc");
        String keyword = queryVo.getKeyword();
        if (StrUtil.strCheckNotNull(keyword)) {
            // keyword is not null or empty
            params.put("keyword", keyword);
        }
        log.info("查询信息:{}", JSON.toJSONString(params));
        Integer count = commonService.getBySqlId(CustomerInfo.class, "queryCount", params);
        List<CustomerInfo> data = commonService.getListBySqlId(CustomerInfo.class, "queryData", params);
        List<CustomerResponseVo> list = new LinkedList<>();
        for (CustomerInfo info : data) {
            CustomerResponseVo vo = new CustomerResponseVo();
            vo.setId(String.valueOf(info.getId()));
            vo.setNickname(info.getNickname());
            vo.setName(info.getRealName());
            vo.setTelephone(info.getTelephone());
            String idNumber = info.getIdNumber();
            vo.setIdNumber(idNumber);
            String age = IDNumberUtil.getAgeFromID(idNumber);
            vo.setAge(age);
            String gender = IDNumberUtil.getGender(idNumber);
            vo.setGender(gender);
            vo.setAddress(info.getAddress());
            LocalDateTime localDateTime = info.getLastModifyTime();
            vo.setLastModified(LocalDateTimeUtil.timeToString(localDateTime));
            vo.setComment(info.getComment());
            list.add(vo);
        }
        response.setCount(count);
        response.setData(list);
        return response;
    }

    @Override
    public CustomerInfo getById(Integer id) {
        return commonService.get(id, CustomerInfo.class);
    }

    private void paramsCheck(CustomerInfo customerInfo) throws Exception {
        if (!StrUtil.strCheckNotNull(customerInfo.getNickname())) {
            throw new Exception("网名不能为空");
        }
        String telephone = customerInfo.getTelephone();
        if (!StrUtil.strCheckNotNull(telephone)) {
            throw new Exception("电话号码不能为空");
        }
        if (!telephone.matches("1[3-9]\\d{9}")) {
            throw new Exception("电话号码不符合规则");
        }
        String idNumber = customerInfo.getIdNumber();
        if (StrUtil.strCheckNotNull(idNumber)) {
            if (!IDNumberUtil.checkID(idNumber)) {
                throw new Exception("身份证号码不符合规则, 未知请留空");
            }
            Integer idCount = commonService.getBySqlId(CustomerInfo.class, "pageCount", "idNumber", idNumber);
            if (idCount != 0) {
                throw new DuplicatedIdNumberException("身份证号码冲突, 此人已存在");
            }
        }
    }
}
