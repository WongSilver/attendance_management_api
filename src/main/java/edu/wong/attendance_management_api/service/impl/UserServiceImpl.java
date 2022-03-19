package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.mapper.UserMapper;
import edu.wong.attendance_management_api.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
