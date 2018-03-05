package cn.aegisa.project.service;

import javax.servlet.ServletOutputStream;
import java.io.FileInputStream; /**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 14:24
 */
public interface ReportService {
    void reportCommonTable(Integer aid, FileInputStream in, ServletOutputStream outputStream);
}
