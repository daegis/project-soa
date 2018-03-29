package cn.aegisa.project.service.impl;

import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.WeightRecord;
import cn.aegisa.project.service.RecordService;
import cn.aegisa.project.vo.ValueResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        
        return response;
    }
}
