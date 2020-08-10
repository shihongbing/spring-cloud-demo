package com.saltfishsoft.springclouddemo.accserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.saltfishsoft.springclouddemo.accserver.domain.Role;
import com.saltfishsoft.springclouddemo.accserver.repository.RoleRepository;
import com.saltfishsoft.springclouddemo.baseserver.controller.BaseController;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.saltfishsoft.springclouddemo.common.constant.BusinessConstants.FAILED_CODE;
import static com.saltfishsoft.springclouddemo.common.constant.BusinessConstants.REST_BASE_URL;

/**
 * Created by Shihongbing on 2020/7/1.
 */
@RestController
@RequestMapping(REST_BASE_URL+"/role")
@Slf4j
public class RoleController extends BaseController{

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/get/{id}")
    public RequestResult queryById(@PathVariable("id") String id){
        log.debug("execute RoleRest queryById query id is:{}",id);
        Role role = roleRepository.findOne(id);
        return role == null ? new RequestResult(FAILED_CODE,"没有找到ID为:"+id+"的角色") : new RequestResult<Role>(role);
    }

    @PostMapping("/add")
    public RequestResult add(@Validated({Role.Add.class}) @RequestBody Role role){
        log.debug("execute RoleRest add post data is:{}", JSONObject.toJSONString(role));
        //roleRepository.add(role);
        return RequestResult.build(role);
    }


}
