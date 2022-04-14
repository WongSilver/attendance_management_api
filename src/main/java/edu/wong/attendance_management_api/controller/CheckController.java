package edu.wong.attendance_management_api.controller;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.dto.CheckDateDTO;
import edu.wong.attendance_management_api.mapper.CheckMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
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

}
