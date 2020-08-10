package com.saltfishsoft.springclouddemo.auth.service.remote;

import com.saltfishsoft.springclouddemo.auth.model.dto.UserDto;
import com.saltfishsoft.springclouddemo.auth.service.remote.fallbackfactory.AccountFeignClientFallbackFactory;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Shihongbing on 2020/7/17.
 */
@FeignClient(value = "account-server",fallbackFactory = AccountFeignClientFallbackFactory.class)
@RequestMapping(value = "/api")
public interface AccoutFeignClient {

    @RequestMapping(value = "/user/findByAccount")
    RequestResult<UserDto> findByAccountEquals(@RequestParam("account") String account);
}
