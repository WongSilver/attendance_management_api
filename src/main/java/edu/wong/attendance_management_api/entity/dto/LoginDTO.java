package edu.wong.attendance_management_api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

    //    用户名
    @NotBlank(message = "用户名不能为空")
    private String name;


    //    用户密码
    @NotBlank(message = "密码不能为空")
    private String password;

}
