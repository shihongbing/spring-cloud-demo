package com.saltfishsoft.springclouddemo.auth.security.integration.authenticator;


/**
 * Created by Shihongbing on 2019/10/17.
 */
public abstract class AbstractPreparableIntegrationAuthenticator implements IntegrationAuthenticator{

    @Override
    public abstract Object authenticate(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract void prepare(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract boolean support(IntegrationAuthentication integrationAuthentication);

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }

}
