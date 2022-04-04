package edu.wong.attendance_management_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.wong.attendance_management_api.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper extends BaseMapper<Account> {
    //    通过用户ID 查询 用户所有信息
    List<Account> selectInfoByUserId(@Param("id") int id);
}
