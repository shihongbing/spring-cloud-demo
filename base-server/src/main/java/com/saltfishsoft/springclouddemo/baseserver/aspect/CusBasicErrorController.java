package com.saltfishsoft.springclouddemo.baseserver.aspect;

import com.saltfishsoft.springclouddemo.common.constant.BusinessConstants;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Shihongbing on 2020/6/22.
 */
@RestController
public class CusBasicErrorController {

    @Autowired
    private BasicErrorController basicErrorController;

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RequestResult error(HttpServletRequest request, HttpServletResponse response){
        //获取异常返回
        ResponseEntity<Map<String, Object>> errorDetail = basicErrorController.error(request);
        //自行组织返回数据
        return RequestResult.build(BusinessConstants.FAILED_CODE,errorDetail.getBody().get("message").toString(),errorDetail.getBody());
    }
}
