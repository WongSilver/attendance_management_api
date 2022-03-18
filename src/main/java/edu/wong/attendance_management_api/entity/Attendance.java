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
@TableName("t_attendance")
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请假学生ID
     */
    private Integer studentId;

    /**
     * 请假的课程ID
     */
    private Integer courseId;

    /**
     * 请假的时间
     */
    private LocalDateTime date;

    /**
     * 类型
     */
    private String tpye;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getTpye() {
        return tpye;
    }

    public void setTpye(String tpye) {
        this.tpye = tpye;
    }

    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + id +
            ", studentId=" + studentId +
            ", courseId=" + courseId +
            ", date=" + date +
            ", tpye=" + tpye +
        "}";
    }
}
