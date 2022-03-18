package edu.wong.attendance_management_api.controller;

import edu.wong.attendance_management_api.entity.Student;
import edu.wong.attendance_management_api.service.IStudentService;
import edu.wong.attendance_management_api.util.ResponseFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-18
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    IStudentService service;

    @GetMapping("/index")
    @ResponseBody
    public ResponseFormatUtil index() {
        return ResponseFormatUtil.successful(service.getById(1));
    }

}
