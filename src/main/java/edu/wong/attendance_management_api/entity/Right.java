package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName("t_right")
public class Right implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 可以请求的url，可以正则
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限类型。1菜单，2按钮，3其他
     */
    private Integer type;

    /**
     * 权限状态。1正常，0冻结
     */
    private Integer status;

    /**
     * 权限创建时间
     */
    private LocalDateTime createTime;

    /**
     * 权限备注
     */
    private String remark;

    private Integer pid;

    private String pagePath;

    @TableField(exist = false)
    private List<Right> rightList;
}
