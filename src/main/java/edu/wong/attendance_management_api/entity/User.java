package edu.wong.attendance_management_api.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Alias("ID")
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Alias("用户名")
    private String name;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Alias("密码")
    private String password;

    /**
     * 用户手机号
     */
    @Alias("手机号")
    private String telephone;

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Alias("邮箱")
    private String mail;

    /**
     * 所在班级ID
     */

    @Alias("班级")
    private Integer groupId;

    /**
     * 用户状态。1正常，0冻结，2删除
     */

    @Alias("状态")
    private Integer status;

    /**
     * 上次登录的时间
     */

    @Alias("上次登录时间")
    private LocalDateTime lastTime;

    /**
     * 用户创建的时间
     */
    @Alias("用户创建时间")
    private LocalDateTime createTime;

}
