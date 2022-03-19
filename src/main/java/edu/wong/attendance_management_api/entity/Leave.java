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
 * @since 2022-03-20
 */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public LocalDateTime getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Leave{" +
            "id=" + id +
            ", userId=" + userId +
            ", leaveDate=" + leaveDate +
            ", reason=" + reason +
            ", remark=" + remark +
            ", state=" + state +
            ", createDate=" + createDate +
        "}";
    }
}
