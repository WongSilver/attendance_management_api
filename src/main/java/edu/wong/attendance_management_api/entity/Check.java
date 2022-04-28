package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
@TableName("t_check")
public class Check implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请假学生ID
     */
    private Integer userId;

    /**
     * 请假的班级ID
     */
    private Integer groupId;

    /**
     * 开始请假时间
     */
    private ZonedDateTime startDate;

    /**
     * 结束请假时间
     */
    private ZonedDateTime endDate;
    /**
     * 类型
     */
    private String type;

    /**
     * 请假状态：0请假 1迟到 3旷课
     */
    private Integer status;

    /**
     * 是否批假0待审核，1批准，-1拒绝
     */
    private Integer yorn;

    /**
     * 描述
     */
    private String description;
}
