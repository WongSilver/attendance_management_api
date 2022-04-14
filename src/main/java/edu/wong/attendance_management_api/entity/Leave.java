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
@TableName("t_leave")
public class Leave implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请假的学生
     */
    private Integer userId;

    /**
     * 需要请假的时间
     */
    private LocalDateTime leaveDate;

    /**
     * 请假的理由
     */
    private String reason;

    /**
     * 教师的回复
     */
    private String remark;

    /**
     * 是否批假
     */
    private Boolean state;

    /**
     * 申请的时间
     */
    private LocalDateTime createDate;

}
