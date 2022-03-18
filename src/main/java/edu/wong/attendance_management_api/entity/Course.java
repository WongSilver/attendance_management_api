package edu.wong.attendance_management_api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-18
 */
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
    private Integer teacherId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
    public LocalDateTime getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(LocalDateTime courseDate) {
        this.courseDate = courseDate;
    }
    public Integer getSelectedNum() {
        return selectedNum;
    }

    public void setSelectedNum(Integer selectedNum) {
        this.selectedNum = selectedNum;
    }
    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", name=" + name +
            ", teacherId=" + teacherId +
            ", courseDate=" + courseDate +
            ", selectedNum=" + selectedNum +
            ", maxNum=" + maxNum +
            ", info=" + info +
        "}";
    }
}
