package com.saltfishsoft.springclouddemo.auth.service.local;

import com.saltfishsoft.springclouddemo.auth.repository.SystemLogRepository;
import com.saltfishsoft.springclouddemo.baseserver.service.BaseService;
import com.saltfishsoft.springclouddemo.common.domain.SystemLog;

/**
 * Created by Shihongbing on 2020/7/27.
 */
public interface ISystemLogService extends BaseService<SystemLog,String,SystemLogRepository> {
}
