package com.saltfishsoft.springclouddemo.accserver.api;

import com.alibaba.fastjson.JSONObject;
import com.saltfishsoft.springclouddemo.accserver.domain.User;
import com.saltfishsoft.springclouddemo.accserver.model.dto.UserQueryDto;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Shihongbing on 2020/7/17.
 */
public interface UserAPI {

    @GetMapping("/get/{id}")
    RequestResult get(@PathVariable("id") String id);

    @PostMapping("/add")
    RequestResult add(@Validated({User.Add.class}) @RequestBody User user);

    @PostMapping("/modify")
    RequestResult modify(@Validated({User.Modify.class}) @RequestBody User user);


    @PostMapping("/page")
    RequestResult pageQuery(@RequestBody UserQueryDto userQueryDto);

    @PostMapping("/authorize")
    RequestResult authorize(@RequestBody JSONObject postData);

    @RequestMapping("/findByAccount")
    RequestResult<User> findByAccountEquals(@RequestParam("account") String account);
}
