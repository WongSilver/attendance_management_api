package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@TableName("t_role_right")
public class RoleRight implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色权限ID
     */
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer rightId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    @Override
    public String toString() {
        return "RoleRight{" +
            "id=" + id +
            ", roleId=" + roleId +
            ", rightId=" + rightId +
        "}";
    }
}
