package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.Leave;
import edu.wong.attendance_management_api.mapper.LeaveMapper;
import edu.wong.attendance_management_api.service.ILeaveService;
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
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

}
