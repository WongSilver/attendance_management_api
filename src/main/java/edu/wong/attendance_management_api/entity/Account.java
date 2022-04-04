package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("view_user_info")
public class Account {
    private int userId;
    private String userName;
    private String userTelephone;
    private String userMail;
    private int userGroup;
    private String userStatus;
    private String userCreateTime;
    private String userLastTime;
    private int rolId;
    private String roleName;
    private int rightId;
    private String rightUrl;
}
