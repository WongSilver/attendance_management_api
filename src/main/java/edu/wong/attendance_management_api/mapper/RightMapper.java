package edu.wong.attendance_management_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.wong.attendance_management_api.entity.Right;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
public interface RightMapper extends BaseMapper<Right> {
    //    通过角色ID 查询 权限Url
    List<Right> selectRightUrlByRoleID(@Param("id") int id);
}
