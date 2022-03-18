package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.Student;
import edu.wong.attendance_management_api.mapper.StudentMapper;
import edu.wong.attendance_management_api.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-18
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
