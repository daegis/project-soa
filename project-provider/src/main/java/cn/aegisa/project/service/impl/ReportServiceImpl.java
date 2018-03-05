package cn.aegisa.project.service.impl;

import cn.aegisa.project.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.FileInputStream;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/5 14:24
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Override
    public void reportCommonTable(Integer aid, FileInputStream in, ServletOutputStream outputStream) {
        try {
            HSSFWorkbook book = new HSSFWorkbook(in);
            HSSFSheet sheet = book.getSheetAt(0);
            sheet.setForceFormulaRecalculation(true);
            int rowIndex = 1;
            HSSFRow row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue("12345");
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
