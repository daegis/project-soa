package cn.aegisa.project.vo;

import cn.aegisa.project.vo.BasePageRequestVo;
import lombok.Getter;
import lombok.Setter;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/9 14:47
 */
@Setter
@Getter
public class CustomerQueryVo extends BasePageRequestVo {
    private String keyword;
}
