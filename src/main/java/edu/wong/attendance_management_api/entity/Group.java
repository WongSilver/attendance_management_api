package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@TableName("t_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 最后一次操作者
     */
    private String lastOperator;

    /**
     * 最后一次操作时间
     */
    private LocalDateTime lastOperatorTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 班级人数
     */
    private int num;

}
