import cn.aegisa.project.dao.service.ICommonService;
import cn.aegisa.project.model.WeightRecord;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/28 23:34
 */
public class TestClass1 extends BaseJunit4Test {


    @Autowired
    private ICommonService commonService;

    @Test
    public void test01(){
        WeightRecord w = commonService.get(1, WeightRecord.class);
        System.out.println(JSON.toJSONString(w));
    }
}
