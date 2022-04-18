package edu.wong.attendance_management_api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.wong.attendance_management_api.entity.Account;
import edu.wong.attendance_management_api.entity.Right;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.entity.dto.UserDTO;
import edu.wong.attendance_management_api.mapper.UserMapper;
import edu.wong.attendance_management_api.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    UserMapper mapper;

    @Override
    public UserDTO getUserInfo(Integer id) {
        UserDTO userDTO = new UserDTO();

//        把查到的结果包装成UserDTO
        List<Account> temp = mapper.getUserDTO(id);
//        temp.get(0)
        ArrayList<Right> list = new ArrayList<>();
        BeanUtil.copyProperties(temp.get(0), userDTO, "userUrl", "userStatus", "userLastTime", "rolId", "rightId", "rightPid", "rightIcon", "rightPagePath");
        for (Account account : temp) {
            Right right = new Right();
            right.setUrl(account.getRightUrl());
            right.setName(account.getRightName());
            right.setId(account.getRightId());
            right.setPid(account.getRightPid());
            right.setIcon(account.getRightIcon());
            right.setPagePath(account.getRightPagePath());
            list.add(right);
        }
        userDTO.setRights(list);

        return userDTO;
    }
}
