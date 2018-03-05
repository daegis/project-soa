package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.ReportService;
import cn.aegisa.project.utils.IDNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 14:24
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ActivityService activityService;

    @Override
    public void reportCommonTable(Integer aid, FileInputStream in, ServletOutputStream outputStream) {
        ActivityInfo activityInfo = activityService.getById(aid);
        List<JoinInfo> joinInfoList = commonService.getList(JoinInfo.class, "aid", aid);
        // 参加该活动的人员id集合
        List<Integer> customerIdList = joinInfoList.stream().map(JoinInfo::getCid).collect(Collectors.toList());
        if (customerIdList == null || customerIdList.size() == 0) {
            return;
        }
        List<CustomerInfo> customerInfoList = commonService.getListBySqlId(CustomerInfo.class, "selectByIds", "idList", customerIdList);
        Map<Integer, CustomerInfo> mappingCustomer = mappingCustomerList(customerInfoList);
        try {
            HSSFWorkbook book = new HSSFWorkbook(in);
            HSSFSheet sheet = book.getSheetAt(0);
            sheet.setForceFormulaRecalculation(true);
            int rowIndex = 1;
            HSSFRow row;
            for (JoinInfo joinInfo : joinInfoList) {
                Integer cid = joinInfo.getCid();
                CustomerInfo customerInfo = mappingCustomer.get(cid);
                row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(joinInfo.getBusSeat());
                row.createCell(1).setCellValue(customerInfo.getRealName());
                row.createCell(2).setCellValue(customerInfo.getNickname());
                String idNumber = customerInfo.getIdNumber();
                row.createCell(3).setCellValue(IDNumberUtil.getAgeFromID(idNumber));
                row.createCell(4).setCellValue(IDNumberUtil.getGender(idNumber));
                row.createCell(5).setCellValue(activityInfo.getPrice());
                row.createCell(6).setCellValue(joinInfo.getDiscount());
                row.createCell(7).setCellValue(joinInfo.getPrepay());
                row.createCell(8).setCellValue(activityInfo.getPrice() - joinInfo.getDiscount() - joinInfo.getPrepay());
                row.createCell(11).setCellValue(joinInfo.getPayMethod());
                row.createCell(12).setCellValue(idNumber);
                row.createCell(13).setCellValue(customerInfo.getTelephone());
                rowIndex++;
            }
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
