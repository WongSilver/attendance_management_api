package edu.wong.attendance_management_api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.wong.attendance_management_api.entity.Account;
import edu.wong.attendance_management_api.mapper.AccountMapper;
import edu.wong.attendance_management_api.service.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
}
