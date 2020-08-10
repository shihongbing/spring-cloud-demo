package com.saltfishsoft.springclouddemo.auth.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * Created by Administrator on 2018/4/27.
 */
@Entity
@Table(name="oauth_client_details")
@DynamicInsert(true)
@DynamicUpdate(true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OauthClientDetails extends BusinessObject implements ClientDetails {
    private String clientId ;

    private String clientSecret;

    private String resourceIds;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String autoApprove;

    private String manager;

    private String managerPhone;

    private String additionalInfo;


    public OauthClientDetails(){

    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> resourceList = new HashSet<String>(); ;
        if(StringUtils.hasText(resourceIds)) {
            resourceList = StringUtils.commaDelimitedListToSet(resourceIds);
        }
        return resourceList;
    }

    @Override
    public Set<String> getScope() {
        Set<String> scopeList = new HashSet<String>();
        if(StringUtils.hasText(scope)) {
            scopeList = StringUtils.commaDelimitedListToSet(scope);
        }
        return scopeList;
    }

    public String getScopeStr(){
        return scope;
    }


    @Override
    public boolean isScoped() {
        return this.getScope() != null && !this.getScope().isEmpty();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypesList = new HashSet<String>(); ;
        if(StringUtils.hasText(authorizedGrantTypes)) {
            grantTypesList = StringUtils.commaDelimitedListToSet(authorizedGrantTypes);
        }
        return grantTypesList;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> set = new HashSet<String>(); ;
        if(StringUtils.hasText(webServerRedirectUri)) {
            set = StringUtils.commaDelimitedListToSet(webServerRedirectUri);
        }
        return set;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<String> authStrSet = new HashSet<String>(); ;
        if(StringUtils.hasText(authorities)) {
            authStrSet = StringUtils.commaDelimitedListToSet(authorities);
        }
        Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
        for (String authStr : authStrSet) {
            authoritySet.add(new SimpleGrantedAuthority(authStr));
        }
        return authoritySet;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return autoApprove.equals("true") || scope.matches(autoApprove);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> info = new HashMap<String, Object>();
        info = JSON.parseObject(additionalInfo,new TypeReference<Map<String,Object>>() {});
        return info;
    }


    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            OauthClientDetails other = (OauthClientDetails)obj;
            if(this.accessTokenValidity == null) {
                if(other.accessTokenValidity != null) {
                    return false;
                }
            } else if(!this.accessTokenValidity.equals(other.accessTokenValidity)) {
                return false;
            }

            if(this.refreshTokenValidity== null) {
                if(other.refreshTokenValidity != null) {
                    return false;
                }
            } else if(!this.refreshTokenValidity.equals(other.refreshTokenValidity)) {
                return false;
            }

            if(this.authorities == null) {
                if(other.authorities != null) {
                    return false;
                }
            } else if(!this.authorities.equals(other.authorities)) {
                return false;
            }

            if(this.authorizedGrantTypes == null) {
                if(other.authorizedGrantTypes != null) {
                    return false;
                }
            } else if(!this.authorizedGrantTypes.equals(other.authorizedGrantTypes)) {
                return false;
            }

            if(this.clientId == null) {
                if(other.clientId != null) {
                    return false;
                }
            } else if(!this.clientId.equals(other.clientId)) {
                return false;
            }

            if(this.clientSecret == null) {
                if(other.clientSecret != null) {
                    return false;
                }
            } else if(!this.clientSecret.equals(other.clientSecret)) {
                return false;
            }

            if(this.webServerRedirectUri == null) {
                if(other.webServerRedirectUri != null) {
                    return false;
                }
            } else if(!this.webServerRedirectUri.equals(other.webServerRedirectUri)) {
                return false;
            }

            if(this.resourceIds == null) {
                if(other.resourceIds != null) {
                    return false;
                }
            } else if(!this.resourceIds.equals(other.resourceIds)) {
                return false;
            }

            if(this.scope == null) {
                if(other.scope != null) {
                    return false;
                }
            } else if(!this.scope.equals(other.scope)) {
                return false;
            }

            if(this.additionalInfo == null) {
                if(other.additionalInfo != null) {
                    return false;
                }
            } else if(!this.additionalInfo.equals(other.additionalInfo)) {
                return false;
            }

            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.accessTokenValidity == null?0:this.accessTokenValidity.intValue());
        result = 31 * result + (this.refreshTokenValidity == null?0:this.refreshTokenValidity.intValue());
        result = 31 * result + (this.authorities == null?0:this.authorities.hashCode());
        result = 31 * result + (this.authorizedGrantTypes == null?0:this.authorizedGrantTypes.hashCode());
        result = 31 * result + (this.clientId == null?0:this.clientId.hashCode());
        result = 31 * result + (this.clientSecret == null?0:this.clientSecret.hashCode());
        result = 31 * result + (this.webServerRedirectUri == null?0:this.webServerRedirectUri.hashCode());
        result = 31 * result + (this.resourceIds == null?0:this.resourceIds.hashCode());
        result = 31 * result + (this.scope == null?0:this.scope.hashCode());
        result = 31 * result + (this.additionalInfo == null?0:this.additionalInfo.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "BaseClientDetails [clientId=" + this.clientId + ", clientSecret=" + this.clientSecret + ", " +
                "scope=" + this.scope + ", resourceIds=" + this.resourceIds + ", " +
                "authorizedGrantTypes=" + this.authorizedGrantTypes + ", " +
                "webServerRedirectUri=" + this.webServerRedirectUri + ", authorities=" + this.authorities + ", " +
                "accessTokenValiditySeconds=" + this.accessTokenValidity + ", " +
                "refreshTokenValiditySeconds=" + this.refreshTokenValidity + ", additionalInformation=" + this.additionalInfo + "]";
    }

    public static void main(String args[]){
        Set<String> set = new HashSet<String>(); ;
        if(StringUtils.hasText("www.baidu.com")) {
            set = StringUtils.commaDelimitedListToSet("www.baidu.com");
        }
        System.out.println(set);
    }


}
