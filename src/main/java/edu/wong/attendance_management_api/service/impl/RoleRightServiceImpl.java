package edu.wong.attendance_management_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.wong.attendance_management_api.entity.RoleRight;
import edu.wong.attendance_management_api.entity.dto.RoleRightDTO;
import edu.wong.attendance_management_api.mapper.RoleRightMapper;
import edu.wong.attendance_management_api.service.IRoleRightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleRightServiceImpl extends ServiceImpl<RoleRightMapper, RoleRight> implements IRoleRightService {

    @Resource
    RoleRightMapper mapper;

    //开启事务，失败时回滚
    @Transactional
    public void syncRight(RoleRightDTO dto) {
        QueryWrapper<RoleRight> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", dto.getRoleId());
        mapper.delete(wrapper);        //先删除当前用户的所有权限
        List<Integer> rightIds = dto.getRightIds();
        rightIds.forEach(i -> {
            RoleRight roleRight = new RoleRight();
            roleRight.setRoleId(dto.getRoleId());
            roleRight.setRightId(i);
            mapper.insert(roleRight);
        });
    }

    @Override
    public List<Integer> getRightList(Integer id) {
        List<Integer> list = new ArrayList<>();
        QueryWrapper<RoleRight> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        List<RoleRight> roleRights = mapper.selectList(wrapper);
        roleRights.forEach(i -> list.add(i.getRightId()));
        return list;
    }
}
