package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("view_user_info")
public class Account {

    @TableId(type = IdType.AUTO)
    private Integer userId;
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
    private String rightName;
    private int rightPid;
    private String rightIcon;
    private String rightPagePath;
}
