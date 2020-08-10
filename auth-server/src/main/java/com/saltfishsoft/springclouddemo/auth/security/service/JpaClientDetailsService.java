package com.saltfishsoft.springclouddemo.auth.security.service;
import com.saltfishsoft.springclouddemo.auth.repository.OauthClientDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * oauth2客户端服务类
 */
@Service("jpaClientDetailsService")
@Slf4j
public class JpaClientDetailsService implements ClientDetailsService{


    private OauthClientDetailsRepository oauthClientRepository;

    @Autowired
    public void setOauthClientRepository(OauthClientDetailsRepository oauthClientRepository) {
        this.oauthClientRepository = oauthClientRepository;
    }

    /**
     * 通过客户端ID查询oauth2客户端
     * @param clientId
     * @return
     * @throws ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = oauthClientRepository.findByClientIdEquals(clientId);
        if(clientDetails == null){
            log.error("Client with id:"+clientId +" is not found");
            throw new ClientRegistrationException(String.format("Client with id %s not found", clientId));
        }
        return clientDetails;
    }
}
