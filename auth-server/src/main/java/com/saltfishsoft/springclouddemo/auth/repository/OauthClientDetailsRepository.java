package com.saltfishsoft.springclouddemo.auth.repository;

import com.saltfishsoft.springclouddemo.auth.domain.OauthClientDetails;
import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
 * oauth2客户端Repository类
 */
@Repository
public interface OauthClientDetailsRepository extends IBaseRepository<OauthClientDetails,String> {

    public OauthClientDetails findByClientIdAndStatusEquals(String clientId, String status);

    public OauthClientDetails findByClientIdEquals(String clientId);
}
