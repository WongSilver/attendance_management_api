package edu.wong.attendance_management_api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.Check;
import edu.wong.attendance_management_api.entity.dto.CheckDateDTO;
import edu.wong.attendance_management_api.mapper.CheckMapper;
import edu.wong.attendance_management_api.util.CheckFieldIsNullUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/check")
public class CheckController {
    @Resource
    CheckMapper checkMapper;

    @GetMapping("/homeList")
    public ResponseFormat getCheckList() {
        HashMap<String, Object> map = new HashMap<>();

        List<CheckDateDTO> checkByDates = checkMapper.selectLeaveNumByDate();
        List<String> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        checkByDates.forEach(checkByDate -> {
            x.add(checkByDate.getByDate());
            y.add(checkByDate.getCountNum());
        });
        System.out.println(x);
        System.out.println(y);
        map.put("x", x);
        map.put("y", y);

        return ResponseFormat.successful(map);
    }

    // 创建请假单
    @PostMapping("/create")
    public ResponseFormat createCheck(@RequestBody Check check) {
        System.out.println("------------" + check);
        String[] fieldNames = {"userId", "groupId", "startDate", "endDate", "type"};
        try {
            if (CheckFieldIsNullUtil.isNull(check, fieldNames)) {
                return ResponseFormat.fail("请填写具体的表单内容");
            }
        } catch (IllegalAccessException e) {
            return ResponseFormat.fail("表单不符合标准");
        }
        checkMapper.insert(check);
        return ResponseFormat.successful("已通知辅导员，请不用重复提交申请");
    }

    /**
     * 根据班级id 查询待批阅假信息
     */
    @GetMapping("/list/{id}")
    public ResponseFormat list(@PathVariable Integer id) {
        QueryWrapper<Check> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", id);
        wrapper.eq("yorn", 0);
        wrapper.orderByDesc("id");
        List<Check> checks = checkMapper.selectList(wrapper);
        return ResponseFormat.successful(checks);
    }

    /**
     * 根据Check的id 批假
     */
    @GetMapping("/read")
    public ResponseFormat list(@RequestParam Integer id, @RequestParam Integer yorn) {
        QueryWrapper<Check> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.orderByDesc("id");
        Check check = new Check();
        check.setYorn(yorn);
        int checks = checkMapper.update(check, wrapper);
        return ResponseFormat.successful(checks);
    }

    /**
     * 获取用户请假历史
     */
    @GetMapping("/userHistory/{id}")
    public ResponseFormat userHistory(@PathVariable int id) {
        QueryWrapper<Check> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        wrapper.orderByDesc("id");
        List<Check> checks = checkMapper.selectList(wrapper);
        return ResponseFormat.successful(checks);
    }

    /**
     * 获取班级请假历史
     */
    @GetMapping("/groupHistory/{id}")
    public ResponseFormat groupHistory(@PathVariable int id) {
        QueryWrapper<Check> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", id);
        wrapper.ne("yorn", 0);
        wrapper.orderByDesc("id");
        List<Check> checks = checkMapper.selectList(wrapper);
        return ResponseFormat.successful(checks);
    }

}
