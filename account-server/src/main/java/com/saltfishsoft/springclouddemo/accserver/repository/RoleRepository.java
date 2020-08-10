package com.saltfishsoft.springclouddemo.accserver.repository;

import com.saltfishsoft.springclouddemo.accserver.domain.Role;
import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Shihongbing on 2020/6/16.
 */
@Repository
public interface RoleRepository extends IBaseRepository<Role,String> {

    List<Role> findByIdIn(Collection<String> ids);
}
