package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.Teacher;
import edu.wong.attendance_management_api.mapper.TeacherMapper;
import edu.wong.attendance_management_api.service.ITeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

}
