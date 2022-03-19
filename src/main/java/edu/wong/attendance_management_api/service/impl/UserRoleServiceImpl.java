package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.UserRole;
import edu.wong.attendance_management_api.mapper.UserRoleMapper;
import edu.wong.attendance_management_api.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
