package cn.aegisa.project.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * ActivityInfo Entity.
 */
@Data
public class ActivityInfo implements Serializable {

    private Integer id;

    private String activityName;

    private LocalDateTime activityDate;

    private Integer dayCount;

    private Integer price;

    private LocalDateTime createTime;

    private Integer isDone;

}

