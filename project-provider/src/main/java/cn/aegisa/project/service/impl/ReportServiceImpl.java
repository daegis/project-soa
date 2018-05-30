package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.ReportService;
import cn.aegisa.project.utils.IDNumberUtil;
import cn.aegisa.project.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 14:24
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ActivityService activityService;

    @Override
    public void reportCommonTable(Integer aid, String type, FileInputStream in, ServletOutputStream outputStream) {
        if (type.equals("zuoweibiao")) {
            busProcessor(aid, type, in, outputStream);
        } else if (type.equals("jiucanbiao")) {
            eatProcessor(aid, type, in, outputStream);
        } else if (type.equals("fangjianbiao")) {
            sleepProcessor(aid, type, in, outputStream);
        }
    }

    private void sleepProcessor(Integer aid, String type, FileInputStream in, ServletOutputStream outputStream) {
        ActivityInfo activityInfo = activityService.getById(aid);
        List<JoinInfo> joinInfoList = commonService.getList(JoinInfo.class, "aid", aid);
        // 参加该活动的人员id集合
        List<Integer> customerIdList = joinInfoList.stream().map(JoinInfo::getCid).collect(Collectors.toList());
        if (customerIdList == null || customerIdList.size() == 0) {
            return;
        }
        List<CustomerInfo> customerInfoList = commonService.getListBySqlId(CustomerInfo.class, "selectByIds", "idList", customerIdList);
        Map<Integer, CustomerInfo> mappingCustomer = mappingCustomerList(customerInfoList);
        joinInfoList.sort(new Comparator<JoinInfo>() {
            @Override
            public int compare(JoinInfo o1, JoinInfo o2) {
                Integer o1Seat = o1.getRoomId();
                Integer o2Seat = o2.getRoomId();
                if (o1Seat == null) {
                    return 1;
                }
                if (o2Seat == null) {
                    return -1;
                }
                return o1Seat - o2Seat;
            }
        });
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
                Integer busSeat = joinInfo.getBusSeat();
                if (busSeat != null) {
                    row.createCell(15).setCellValue(busSeat);
                }
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
                row.createCell(12).setCellValue("-" + idNumber + "-");
                row.createCell(13).setCellValue("-" + customerInfo.getTelephone() + "-");
                Integer tableSeat = joinInfo.getTableSeat();
                if (tableSeat != null) {
                    row.createCell(14).setCellValue(tableSeat);
                }
                Integer roomId = joinInfo.getRoomId();
                if (roomId != null) {
                    row.createCell(0).setCellValue(roomId);
                }
                rowIndex++;
            }
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eatProcessor(Integer aid, String type, FileInputStream in, ServletOutputStream outputStream) {
        ActivityInfo activityInfo = activityService.getById(aid);
        List<JoinInfo> joinInfoList = commonService.getList(JoinInfo.class, "aid", aid);
        // 参加该活动的人员id集合
        List<Integer> customerIdList = joinInfoList.stream().map(JoinInfo::getCid).collect(Collectors.toList());
        if (customerIdList == null || customerIdList.size() == 0) {
            return;
        }
        List<CustomerInfo> customerInfoList = commonService.getListBySqlId(CustomerInfo.class, "selectByIds", "idList", customerIdList);
        Map<Integer, CustomerInfo> mappingCustomer = mappingCustomerList(customerInfoList);
        joinInfoList.sort(new Comparator<JoinInfo>() {
            @Override
            public int compare(JoinInfo o1, JoinInfo o2) {
                Integer o1Seat = o1.getTableSeat();
                Integer o2Seat = o2.getTableSeat();
                if (o1Seat == null) {
                    return 1;
                }
                if (o2Seat == null) {
                    return -1;
                }
                return o1Seat - o2Seat;
            }
        });
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
                Integer busSeat = joinInfo.getBusSeat();
                if (busSeat != null) {
                    row.createCell(14).setCellValue(busSeat);
                }
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
                row.createCell(12).setCellValue("-" + idNumber + "-");
                row.createCell(13).setCellValue("-" + customerInfo.getTelephone() + "-");
                Integer tableSeat = joinInfo.getTableSeat();
                if (tableSeat != null) {
                    row.createCell(0).setCellValue(tableSeat);
                }
                Integer roomId = joinInfo.getRoomId();
                if (roomId != null) {
                    row.createCell(15).setCellValue(roomId);
                }
                rowIndex++;
            }
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void busProcessor(Integer aid, String type, FileInputStream in, ServletOutputStream outputStream) {
        ActivityInfo activityInfo = activityService.getById(aid);
        List<JoinInfo> joinInfoList = commonService.getList(JoinInfo.class, "aid", aid);
        // 参加该活动的人员id集合
        List<Integer> customerIdList = joinInfoList.stream().map(JoinInfo::getCid).collect(Collectors.toList());
        if (customerIdList == null || customerIdList.size() == 0) {
            return;
        }
        List<CustomerInfo> customerInfoList = commonService.getListBySqlId(CustomerInfo.class, "selectByIds", "idList", customerIdList);
        Map<Integer, CustomerInfo> mappingCustomer = mappingCustomerList(customerInfoList);
        joinInfoList.sort(new Comparator<JoinInfo>() {
            @Override
            public int compare(JoinInfo o1, JoinInfo o2) {
                Integer o1Seat = o1.getBusSeat();
                Integer o2Seat = o2.getBusSeat();
                if (o1Seat == null) {
                    return 1;
                }
                if (o2Seat == null) {
                    return -1;
                }
                return o1Seat - o2Seat;
            }
        });
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
                Integer busSeat = joinInfo.getBusSeat();
                if (busSeat != null) {
                    row.createCell(0).setCellValue(busSeat);
                }
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
                row.createCell(12).setCellValue("-" + idNumber + "-");
                row.createCell(13).setCellValue("-" + customerInfo.getTelephone() + "-");
                Integer tableSeat = joinInfo.getTableSeat();
                if (tableSeat != null) {
                    row.createCell(14).setCellValue(tableSeat);
                }
                Integer roomId = joinInfo.getRoomId();
                if (roomId != null) {
                    row.createCell(15).setCellValue(roomId);
                }
                rowIndex++;
            }
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportInsurance(Integer aid, FileInputStream in, ServletOutputStream outputStream) {
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
            int rowIndex = 15;
            HSSFRow row;
            for (JoinInfo joinInfo : joinInfoList) {
                Integer cid = joinInfo.getCid();
                CustomerInfo customerInfo = mappingCustomer.get(cid);
                row = sheet.getRow(rowIndex);
                row.getCell(2).setCellValue(customerInfo.getRealName());
                row.getCell(3).setCellValue("身份证");
                row.getCell(4).setCellValue(customerInfo.getIdNumber());
                row.getCell(7).setCellValue(customerInfo.getTelephone());
                rowIndex++;
            }
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, String>> getAnnounceCustomers(Integer aid) {
        List<Map<String, String>> list = new LinkedList<>();
        List<JoinInfo> joinInfoList = commonService.getList(JoinInfo.class, "aid", aid);
        if (joinInfoList != null && joinInfoList.size() != 0) {
            joinInfoList.sort((o1, o2) -> {
                Integer o1Seat = o1.getBusSeat();
                Integer o2Seat = o2.getBusSeat();
                if (o1Seat == null) {
                    return 1;
                } else if (o2Seat == null) {
                    return -1;
                } else {
                    return o1Seat - o2Seat;
                }
            });
            for (JoinInfo joinInfo : joinInfoList) {
                Integer cid = joinInfo.getCid();
                CustomerInfo customerInfo = commonService.get(cid, CustomerInfo.class);
                Map<String, String> map = new LinkedHashMap<>();
                map.put("name", StrUtil.strCheckNotNull(customerInfo.getRealName()) ? customerInfo.getRealName() : "没有真实姓名");
                map.put("id", StrUtil.strCheckNotNull(customerInfo.getIdNumber()) ? customerInfo.getIdNumber() : "错误的身份证号");
                list.add(map);
            }
        }
        int number = 1;
        for (Map<String, String> map : list) {
            map.put("number", String.valueOf(number));
            number++;
        }
        return list;
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
