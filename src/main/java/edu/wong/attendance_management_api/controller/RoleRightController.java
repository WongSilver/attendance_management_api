package edu.wong.attendance_management_api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.RoleRight;
import edu.wong.attendance_management_api.mapper.RoleRightMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        System.out.println("----------------id" + id);
        List<Integer> list = new ArrayList<>();
        QueryWrapper<RoleRight> roleRightQueryWrapper = new QueryWrapper<>();
        roleRightQueryWrapper.eq("role_id", id);
        List<RoleRight> roleRights = mapper.selectList(roleRightQueryWrapper);
        roleRights.forEach(i -> list.add(i.getRightId()));
//        System.out.println("-------------" + list);
        return ResponseFormat.successful(list);
    }

}
