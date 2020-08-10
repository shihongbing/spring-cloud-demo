package com.saltfishsoft.springclouddemo.auth.repository;

import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import com.saltfishsoft.springclouddemo.common.domain.SystemLog;
import org.springframework.stereotype.Repository;

/**
 * Created by Shihongbing on 2020/7/2.
 */
@Repository
public interface SystemLogRepository extends IBaseRepository<SystemLog,String> {
}
