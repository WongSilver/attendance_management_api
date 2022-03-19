package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.Role;
import edu.wong.attendance_management_api.mapper.RoleMapper;
import edu.wong.attendance_management_api.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
