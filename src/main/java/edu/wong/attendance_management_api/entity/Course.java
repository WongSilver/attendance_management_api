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
@TableName("t_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 任课教师
     */
    private Integer userId;

    /**
     * 课程时间
     */
    private LocalDateTime courseDate;

    /**
     * 选课人数
     */
    private Integer selectedNum;

    /**
     * 最大选课人数
     */
    private Integer maxNum;

    /**
     * 课程信息
     */
    private String info;

}
