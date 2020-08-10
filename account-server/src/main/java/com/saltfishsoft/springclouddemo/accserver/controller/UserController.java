package com.saltfishsoft.springclouddemo.accserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.saltfishsoft.springclouddemo.accserver.api.UserAPI;
import com.saltfishsoft.springclouddemo.accserver.domain.User;
import com.saltfishsoft.springclouddemo.accserver.model.dto.UserQueryDto;
import com.saltfishsoft.springclouddemo.accserver.service.IUserService;
import com.saltfishsoft.springclouddemo.baseserver.controller.BaseController;
import com.saltfishsoft.springclouddemo.baseserver.holder.RunTimeUserHolder;
import com.saltfishsoft.springclouddemo.baseserver.model.RunTimeUser;
import com.saltfishsoft.springclouddemo.common.annotation.Log;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.saltfishsoft.springclouddemo.common.constant.BusinessConstants.FAILED_CODE;
import static com.saltfishsoft.springclouddemo.common.constant.BusinessConstants.REST_BASE_URL;

/**
 * Created by Shihongbing on 2020/6/18.
 */
@RestController
@RequestMapping(REST_BASE_URL+"/user")
@Slf4j
public class UserController extends BaseController implements UserAPI{

    @Resource(name ="userService")
    private IUserService userService;

    /**
     * id查询用户
     * @param id
     * @return
     */
    public RequestResult get(@PathVariable("id") String id){
        log.debug("execute userRest queryById query id is:{}",id);
        User user = userService.get(id);
        return user == null ? new RequestResult(FAILED_CODE,"没有找到ID为:"+id+"的帐号") : new RequestResult<User>(user);
    }

    /**
     * 用户新增
     * @param user
     * @return
     */
    @Log("用户新增")
    public RequestResult add(@Validated({User.Add.class}) @RequestBody User user){
        log.debug("execute userRest add post data is:{}", JSONObject.toJSONString(user));
        userService.add(user);
        return RequestResult.build(user);
    }

    /**
     * 用户修改
     * @param user
     * @return
     */
    @Log("用户修改")
    public RequestResult modify(@RequestBody @Validated({User.Modify.class}) User user){
        log.debug("execute userRest modify post data is:{}", JSONObject.toJSONString(user));
        userService.modify(user);
        return RequestResult.build(user);
    }

    /**
     * 分页查询
     * @param userQueryDto
     * @return
     */
    public RequestResult pageQuery(@RequestBody UserQueryDto userQueryDto){
        log.debug("execute userRest pageQuery post data is:{}", JSONObject.toJSONString(userQueryDto));
        Page<User> page =  userService.findAll(userQueryDto.toSpec(), userQueryDto.buildPageRequest());
        return RequestResult.buildPageResult(page);
    }

    /**
     * 用户角色授权
     * @param
     * @param
     * @return
     */
    @Log("用户角色授权")
    public RequestResult authorize(@RequestBody JSONObject postData){
        Object userId = postData.get("userId");
        Preconditions.checkNotNull(userId,"userId is empty");
        JSONArray rolesIds = postData.getJSONArray("roleIds");
        Preconditions.checkArgument(rolesIds!=null && rolesIds.size()!=0,"roleIds is empty");
        User user = userService.authorizeRoles(userId.toString(),rolesIds.toArray(new String[]{}));
        return RequestResult.build(user);
    }

    @Override
    public RequestResult<User> findByAccountEquals(@RequestParam("account") String account) {
        Preconditions.checkNotNull(account,"RequestParam error : account is empty");
        User user = userService.getRepository().findByAccountEquals(account);
        return user == null ? new RequestResult(FAILED_CODE,"没有该用户") : new RequestResult<User>(user);
    }

    @GetMapping("/test")
    @Log(value="日志记录测试")
    public RequestResult msgTest(@RequestBody User user){
        log.debug("execute userRest msgTest query id is:{}",user.getId());
//        SystemLog log = new SystemLog();
//        log.setAccount("shihongbing");
//        log.setClientIp("127.0.0.1");
//        log.setModuleName("账号管理");
//        log.setResult("操作成功");
        //systemLogSender.send(log);
        user = userService.get(user.getId());
        return user == null ? new RequestResult(FAILED_CODE,"没有找到ID为:"+user.getId()+"的帐号") : new RequestResult<User>(user);
    }

    @RequestMapping(value = "/session")
    public Map<String, Object> getSession(HttpServletRequest request) {
        request.getSession().setAttribute("username", "admin");
        String userName = (String) request.getSession().getAttribute("username");
        System.out.println("userName:" + userName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sessionId", request.getSession().getId());
        return map;
    }

    @RequestMapping(value = "/get")
    public String get(HttpServletRequest request){
        String userName=(String)request.getSession().getAttribute("username");
        return userName;
    }

    @Autowired
    private RunTimeUserHolder runTimeUserHolder;

    @GetMapping("/currentUser")
    public RunTimeUser currentUser() {
        return runTimeUserHolder.getCurrentUser();
    }
}
