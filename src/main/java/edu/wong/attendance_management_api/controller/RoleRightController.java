package edu.wong.attendance_management_api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.RoleRight;
import edu.wong.attendance_management_api.entity.dto.RoleRightDTO;
import edu.wong.attendance_management_api.mapper.RoleRightMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/roleRight")
public class RoleRightController {
    @Resource
    RoleRightMapper mapper;

    @GetMapping("/checkedRight/{id}")
    public ResponseFormat checkedRight(@PathVariable Integer id) {
        List<Integer> list = new ArrayList<>();
        QueryWrapper<RoleRight> roleRightQueryWrapper = new QueryWrapper<>();
        roleRightQueryWrapper.eq("role_id", id);
        List<RoleRight> roleRights = mapper.selectList(roleRightQueryWrapper);
        roleRights.forEach(i -> list.add(i.getRightId()));
        return ResponseFormat.successful(list);
    }

    @Transactional
    @PostMapping("/setRoleRight")
    public ResponseFormat setRoleRight(@RequestBody RoleRightDTO dto) {
        if (dto.getRoleId() == 1) {
            return ResponseFormat.operate(200, "不能编辑管理员权限", null);
        }

        QueryWrapper<RoleRight> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", dto.getRoleId());
//        进来先删除当前用户的所有权限
        mapper.delete(wrapper);

        List<Integer> rightIds = dto.getRightIds();
        rightIds.forEach(i -> {
            RoleRight roleRight = new RoleRight();
            roleRight.setRoleId(dto.getRoleId());
            roleRight.setRightId(i);
            mapper.insert(roleRight);
        });
        return ResponseFormat.operate(200, "权限设置成功", null);
    }
}
