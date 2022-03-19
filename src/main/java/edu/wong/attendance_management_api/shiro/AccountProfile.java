package edu.wong.attendance_management_api.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 所在班级ID
     */
    private Integer groupId;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户创建日期
     */
    private LocalDateTime createDate;


}
