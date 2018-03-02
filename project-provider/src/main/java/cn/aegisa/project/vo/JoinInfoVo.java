package cn.aegisa.project.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:31
 */
@Setter
@Getter
public class JoinInfoVo {
    private Integer id;
    private Integer aid;
    private Integer cid;
    private String joinDate;
    private Integer discount;
    private Integer prepay;
    private String payMethod;
    private String joinComment;
}
