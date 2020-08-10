package com.saltfishsoft.springclouddemo.auth.service.remote.fallbackfactory;

import com.saltfishsoft.springclouddemo.auth.model.dto.UserDto;
import com.saltfishsoft.springclouddemo.auth.service.remote.AccoutFeignClient;
import com.saltfishsoft.springclouddemo.common.constant.BusinessConstants;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.saltfishsoft.springclouddemo.auth.constant.MessageConstant.REST_SERVER_UNAVAILABLE;

/**
 * Created by Shihongbing on 2020/7/17.
 */
@Component
@Slf4j
public class AccountFeignClientFallbackFactory implements FallbackFactory<AccoutFeignClient> {
    @Override
    public AccoutFeignClient create(Throwable throwable) {
        return new AccoutFeignClient() {
            @Override
            public RequestResult<UserDto> findByAccountEquals(String account) {
                String msg = throwable.getMessage();
                log.error(" AccountFeignClientFallbackFactory method(findByAccountEquals) error:{}",msg);
                return new RequestResult<UserDto>(BusinessConstants.FAILED_CODE,REST_SERVER_UNAVAILABLE);
            }
        };
    }
}
