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
    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }
    public LocalDateTime getLastOperatorTime() {
        return lastOperatorTime;
    }

    public void setLastOperatorTime(LocalDateTime lastOperatorTime) {
        this.lastOperatorTime = lastOperatorTime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + id +
            ", name=" + name +
            ", lastOperator=" + lastOperator +
            ", lastOperatorTime=" + lastOperatorTime +
            ", remark=" + remark +
        "}";
    }
}
