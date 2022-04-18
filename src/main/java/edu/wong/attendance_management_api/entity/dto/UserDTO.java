package edu.wong.attendance_management_api.entity.dto;

import edu.wong.attendance_management_api.entity.Right;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 用于前端登陆时返回所需要的数据
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String userName;
    private String userTelephone;
    private String userMail;
    private Integer userGroup;
    private String userCreateTime;
    private String roleName;
    private Integer pid;
    private String icon;
    private String pagePath;
    private List<Right> rights;
}
