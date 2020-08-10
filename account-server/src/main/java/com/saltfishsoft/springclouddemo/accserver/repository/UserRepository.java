package com.saltfishsoft.springclouddemo.accserver.repository;

import com.saltfishsoft.springclouddemo.accserver.domain.User;
import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends IBaseRepository<User,String> {

    User findByIdCardNoEquals(String idCardNo);

    @Query(" select u from User u join fetch u.roles r where u.account= :account ")
    User findByAccountEquals(@Param("account") String account);
}
