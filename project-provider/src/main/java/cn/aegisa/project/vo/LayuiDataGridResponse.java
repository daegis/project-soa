package cn.aegisa.project.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/9 14:57
 */
@Setter
@Getter
public class LayuiDataGridResponse<T> {
    private Integer code = 0;
    private String msg = "";
    private Integer count;
    private List<T> data;
}
