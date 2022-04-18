package edu.wong.attendance_management_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.wong.attendance_management_api.entity.Account;
import edu.wong.attendance_management_api.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from view_user_info where user_id = #{id}")
    List<Account> getUserDTO(@Param("id") Integer id);
}
