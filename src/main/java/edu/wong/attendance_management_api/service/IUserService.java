package edu.wong.attendance_management_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.entity.dto.UserDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
public interface IUserService extends IService<User> {

    UserDTO getUserInfo(Integer id);
}
