package cn.aegisa.project.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian at 2018/3/29 13:55
 */
@Setter
@Getter
public class ValueResponse {
    private List<String> days;
    private List<Double> xydValue;
    private List<Double> coolValue;
}
