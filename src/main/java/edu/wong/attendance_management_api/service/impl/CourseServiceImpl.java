package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.Course;
import edu.wong.attendance_management_api.mapper.CourseMapper;
import edu.wong.attendance_management_api.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
