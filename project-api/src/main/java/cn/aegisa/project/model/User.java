package cn.aegisa.project.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * User Entity.
 */
@Data
public class User implements Serializable {

    //列信息
    private Integer id;

    private String name;

    private String password;

    private LocalDateTime lastLoginTime;

    private Integer wrongTime;

    private Integer status;

    private LocalDateTime lockTime;

}

