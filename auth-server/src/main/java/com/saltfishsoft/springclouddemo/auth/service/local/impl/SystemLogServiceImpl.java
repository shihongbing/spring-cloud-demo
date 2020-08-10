package com.saltfishsoft.springclouddemo.auth.service.local.impl;

import com.saltfishsoft.springclouddemo.auth.repository.SystemLogRepository;
import com.saltfishsoft.springclouddemo.auth.service.local.ISystemLogService;
import com.saltfishsoft.springclouddemo.baseserver.service.BaseServiceImpl;
import com.saltfishsoft.springclouddemo.common.domain.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Shihongbing on 2020/7/27.
 */
@Service("systemLogService")
@Transactional(rollbackFor=Exception.class)
@Slf4j
public class SystemLogServiceImpl extends BaseServiceImpl<SystemLog,String,SystemLogRepository> implements ISystemLogService{
}
