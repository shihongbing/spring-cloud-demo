package com.saltfishsoft.springclouddemo.auth.security.configuration;

import com.saltfishsoft.springclouddemo.auth.security.filter.IntegrationAuthenticationFilter;
import com.saltfishsoft.springclouddemo.auth.security.service.CustomUserDetailsService;
import com.saltfishsoft.springclouddemo.auth.security.service.JpaClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *认证配置类
 * 微服务直接的相互访问 采用oauth2的客户端模式
 * https://www.cnblogs.com/yueshutong/p/10279346.html
 * https://cloud.tencent.com/developer/article/1450973
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;

    @Autowired
    private JpaClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    private AuthorizationEndpoint authorizationEndpoint;

    /**
     * jwt token
     */
    private TokenEndpoint tokenEndpoint;
    private DefaultTokenServices tokenServices;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

//    @Autowired
//    private RedisConnectionFactory connectionFactory;
//
//
//    @Bean
//    public RedisTokenStore tokenStore() {
//        return new RedisTokenStore(connectionFactory);
//    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    public ApprovalStore approvalStore() {
        JdbcApprovalStore approvalStore = new JdbcApprovalStore(dataSource);
        approvalStore.setAddApprovalStatement(String.format("insert into %s ( %s ) values (?,?,?,?,?,?)", new Object[]{"oauth_approvals", "EXPIRES_AT,STATUS,LAST_MODIFIED_AT,USER_ID,CLIENT_ID,SCOPE"}));
        approvalStore.setDeleteApprovalStatment(String.format("delete from %s where USER_ID=? and CLIENT_ID=? and SCOPE=?", new Object[]{"oauth_approvals"}));
        approvalStore.setExpireApprovalStatement(String.format("update %s set EXPIRES_AT = ? where USER_ID=? and CLIENT_ID=? and scope=?", new Object[]{"oauth_approvals"}));
        approvalStore.setFindApprovalStatement(String.format("select %s from %s where USER_ID=? and CLIENT_ID=?", new Object[]{"EXPIRES_AT,STATUS,LAST_MODIFIED_AT,USER_ID,CLIENT_ID,SCOPE", "oauth_approvals"}));
        approvalStore.setRefreshApprovalStatement(String.format("update %s set EXPIRES_AT=?, STATUS=?, LAST_MODIFIED_AT=? where USER_ID=? and CLIENT_ID=? and SCOPE=?", new Object[]{"oauth_approvals"}));
        return approvalStore;
    }




}
