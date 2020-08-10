package com.saltfishsoft.springclouddemo.accserver;

import com.saltfishsoft.springclouddemo.accserver.domain.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Shihongbing on 2020/6/19.
 */

public class UserRestTest extends UserControllerTest<User> {


    //401错误列子
    @Test
    public void error_example() throws Exception {
        this.mockMvc
                .perform(post("/error")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andDo(document("error-example",
                        responseFields(
                                fieldWithPath("success").description("操作结果"),
                                subsectionWithPath("data").description("具体错误信息描述"),
                                fieldWithPath("code").description("错误编号"),
                                fieldWithPath("message").description("错误信息"))));
    }

    //用户添加测试
    @Test
    public void userAddExample()throws Exception{
        Map<String, Object> user = createUser();
        ConstrainedFields fields = new ConstrainedFields(User.class);
        List<Snippet> snippets = new ArrayList<Snippet>();
        snippets.add(requestFields(
                fields.withPath("account").description("帐号名称"),
                fields.withPath("userName").description("姓名"),
                fields.withPath("password").description("用户密码"),
                fields.withPath("phone").description("手机号"),
                fields.withPath("email").description("邮箱"),
                fields.withPath("gender").description("性别"),
                fields.withPath("idCardNo").description("身份证号"),
                fields.withPath("employeeNo").description("员工编号"),
                fields.withPath("userType").description("用户类型"),
                fields.withPath("birthday").description("生日"),
                fields.withPath("address").description("地址")
        ));
        snippets.add(relaxedResponseFields(
                beneathPath("data").withSubsectionId("data"),
                getFieldDescriptors()
        ));
        snippets.add(commonRequestHeader());
        afterRequest(
                getMockMvc().perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dXNlcjpzZWNyZXQ=")
                        .content(this.objectMapper.writeValueAsString(user))
                ),
                snippets
        );
    }


    private Map<String, Object> createUser(){
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("account","shihongbing");
        user.put("password","qwer123456");
        user.put("userName","史红兵");
        user.put("phone","18313940931");
        user.put("email","piaoldebing@126.com");
        user.put("gender","1");
        user.put("idCardNo","53252719881227201X");
        user.put("employeeNo","10000");
        user.put("userType","1");
        user.put("birthday","2020-06-20 00:00:00");
        user.put("address","云南昆明");
        return user;
    }
}
