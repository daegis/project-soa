package cn.aegisa.project.service;

import javax.servlet.ServletOutputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 14:24
 */
public interface ReportService {
    void reportCommonTable(Integer aid,String type, FileInputStream in, ServletOutputStream outputStream);

    void reportInsurance(Integer aid, FileInputStream in, ServletOutputStream outputStream);

    List<Map<String,String>> getAnnounceCustomers(Integer aid);
}
