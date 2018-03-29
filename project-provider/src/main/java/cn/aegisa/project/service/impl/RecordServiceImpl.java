package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.WeightRecord;
import cn.aegisa.project.service.RecordService;
import cn.aegisa.project.vo.ValueResponse;
import cn.aegisa.project.vo.WeightVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian at 2018/3/29 12:54
 */
@Service
@Slf4j
public class RecordServiceImpl implements RecordService {

    @Autowired
    private ICommonService commonService;

    @Override
    public void saveRecord(String who, String weight) {
        WeightRecord record = new WeightRecord();
        record.setName(who);
        record.setDatetime(LocalDateTime.now());
        double we = 0.0;
        try {
            we = Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            throw new RuntimeException("记录值好像不是数字哦~");
        }
        if (we < 50 || we > 130) {
            throw new RuntimeException("这个数字是不是太离谱了!ლ(ٱ٥ٱლ)");
        }
        record.setWeight(we);
        commonService.save(record);
    }

    @Override
    public ValueResponse queryData() {
        ValueResponse response = new ValueResponse();
        List<WeightRecord> recordList = commonService.getList(WeightRecord.class);
        // 获取今天
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime _15dayAgo = today.minusDays(15);
        // 只看最近十五天的记录
        recordList.removeIf(o -> o.getDatetime().isBefore(_15dayAgo));
        // 获取到日期的集合
        List<LocalDate> localDateList = recordList.stream().map(WeightRecord::getDatetime).map(LocalDateTime::toLocalDate).collect(Collectors.toList());
        // 获取到唯一日期
        Set<LocalDate> localDateSet = new TreeSet<>(localDateList);
        DateTimeFormatter MMdd = DateTimeFormatter.ofPattern("MMdd");
        List<String> strDay = localDateSet.stream().map(o -> o.format(MMdd)).collect(Collectors.toList());
        response.setDays(strDay);
        int dayCount = strDay.size();
        log.info("需要展示{}天的记录", dayCount);
        List<WeightRecord> xydRecordList = recordList.stream().filter(o -> o.getName().equals("yingda")).collect(Collectors.toList());
        List<WeightRecord> shuangRecordList = recordList.stream().filter(o -> o.getName().equals("shuang")).collect(Collectors.toList());
        List<Double> xydList = new LinkedList<>();
        List<Double> shuangList = new LinkedList<>();
        for (LocalDate localDate : localDateSet) {
            WeightRecord xyd = getOneByDay(xydRecordList, localDate);
            WeightRecord shuang = getOneByDay(shuangRecordList, localDate);
            if (xyd != null) {
                xydList.add(xyd.getWeight());
            } else {
                xydList.add(90.0);
            }
            if (shuang != null) {
                shuangList.add(shuang.getWeight());
            } else {
                shuangList.add(50.0);
            }
        }
        response.setXydValue(xydList);
        response.setCoolValue(shuangList);
        return response;
    }

    @Override
    public List<WeightVo> queryList(String name) {
        List<WeightVo> list = new LinkedList<>();
        List<WeightRecord> recordList = commonService.getList(WeightRecord.class, "name", name);
        for (WeightRecord weightRecord : recordList) {
            WeightVo vo = new WeightVo();
            vo.setId(String.valueOf(weightRecord.getId()));
            vo.setName(weightRecord.getName());
            vo.setWeight(String.valueOf(weightRecord.getWeight()) + "（Kg）");
            vo.setDate(weightRecord.getDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            list.add(vo);
        }
        list.sort(Comparator.comparing(WeightVo::getId));
        return list;
    }

    private static WeightRecord getOneByDay(List<WeightRecord> list, LocalDate localDate) {
        for (WeightRecord weightRecord : list) {
            LocalDate dbDate = weightRecord.getDatetime().toLocalDate();
            if (dbDate.isEqual(localDate)) {
                return weightRecord;
            }
        }
        return null;
    }
}
