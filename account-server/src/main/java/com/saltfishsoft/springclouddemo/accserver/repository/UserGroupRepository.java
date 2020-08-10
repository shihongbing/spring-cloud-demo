package com.saltfishsoft.springclouddemo.accserver.repository;

import com.saltfishsoft.springclouddemo.accserver.domain.UserGroup;
import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shihongbing on 2020/6/16.
 */
@Repository
public interface UserGroupRepository extends IBaseRepository<UserGroup,String> {
}
