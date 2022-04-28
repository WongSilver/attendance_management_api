package edu.wong.attendance_management_api.controller;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.dto.RoleRightDTO;
import edu.wong.attendance_management_api.service.IRoleRightService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    IRoleRightService service;

    @GetMapping("/checkedRight/{id}")
    public ResponseFormat checkedRight(@PathVariable Integer id) {
        List<Integer> list = service.getRightList(id);
        return ResponseFormat.successful(list);
    }

    @PostMapping("/setRoleRight")
    public ResponseFormat setRoleRight(@RequestBody RoleRightDTO dto) {
        if (dto.getRoleId() == 1) {
            return ResponseFormat.operate(200, "不能编辑管理员权限", null);
        }
        service.syncRight(dto);
        return ResponseFormat.operate(200, "权限设置成功", null);
    }
}
