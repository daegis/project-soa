package cn.aegisa.project.vo;

import lombok.Data;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:31
 */
@Data
public class JoinInfoVo {
    private Integer id;
    private Integer aid;
    private Integer cid;
    private String joinDate;
    private Integer discount;
    private Integer prepay;
    private String payMethod;
    private String joinComment;

    private String nickname;
    private String realName;
    private String gender;
    private String age;
    private String restPay;
    private Integer busSeat;
    private Integer tableSeat;
    private Integer roomId;
}
