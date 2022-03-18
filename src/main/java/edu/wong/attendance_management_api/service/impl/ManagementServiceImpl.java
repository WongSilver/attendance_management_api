package edu.wong.attendance_management_api.service.impl;

import edu.wong.attendance_management_api.entity.Management;
import edu.wong.attendance_management_api.mapper.ManagementMapper;
import edu.wong.attendance_management_api.service.IManagementService;
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
public class ManagementServiceImpl extends ServiceImpl<ManagementMapper, Management> implements IManagementService {

}
