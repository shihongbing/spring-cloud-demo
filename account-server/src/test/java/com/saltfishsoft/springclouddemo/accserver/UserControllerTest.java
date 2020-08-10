package com.saltfishsoft.springclouddemo.accserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.saltfishsoft.springclouddemo.common.annotation.FieldDesc;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Shihongbing on 2020/6/29.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Setter
@Getter
public class UserControllerTest<T> {

    private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];//获取泛型T的类
    private List<FieldDescriptor> fieldDescriptors = new ArrayList<>();//泛型T的字段说明
    private List<FieldDescriptor> objectResultFieldDescriptors = new ArrayList<>();//返回结果的data为Object的字段说明
    private List<FieldDescriptor> booleanResultFieldDescriptors = new ArrayList<>();//返回结果的data为Boolean的字段说明

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;


    //定义接口文档输出的位置格式
    private final String documentLocation = "{ClassName}/{methodName}";

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        for (Field declaredField : BusinessObject.class.getDeclaredFields()) {
            if(declaredField.getAnnotation(FieldDesc.class)!=null){
                addReponseField(declaredField);
            }
        }
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if(declaredField.getAnnotation(FieldDesc.class)!=null){
                addReponseField(declaredField);
            }
        }

        objectResultFieldDescriptors.addAll(commonRestFiled());
    }


    public void addReponseField(Field declaredField){
        fieldDescriptors.add(fieldWithPath(declaredField.getName()).description(declaredField.getAnnotation(FieldDesc.class).value()));
    }
    public MvcResult afterRequest(ResultActions resultActions, List<Snippet> snippets) throws Exception {
        //为传进来的snippets在前面加上返回结果的字段说明
        snippets.add(0, relaxedResponseFields(
                objectResultFieldDescriptors
        ));
        return resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(
                        document(documentLocation,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                snippets.toArray(new Snippet[0])
                        )
                )
                .andReturn();
    }

    public List<FieldDescriptor> commonRestFiled(){
        ImmutableList<FieldDescriptor> fieldDescriptors = ImmutableList.of(
                fieldWithPath("success").description("操作结果"),
                subsectionWithPath("data").description("接口返回的数据对象"),
                fieldWithPath("code").description("错误编号"),
                fieldWithPath("message").description("错误信息"));
        return fieldDescriptors;
    }

    //共用请求头
    public RequestHeadersSnippet commonRequestHeader(){
        return requestHeaders(
                headerWithName("Authorization").description(
                        "Basic auth credentials"),
                headerWithName("Content-Type").description(
                        "application/json"));
    }

    public static void main(String args[]){
//        Field fields[] = User.class.getDeclaredFields();
//        for(Field field : fields){
//            Class type = field.getType();
//            //System.out.println(type);
//
//            if(type == String.class){
//                System.out.println(field.getName()+":String");
//            }else if(type == int.class || type == Integer.class || type == Float.class || type == float.class || type==Double.class || type==double.class || type == Long.class || type== long.class){
//                System.out.println(field.getName()+":Number");
//            }else if(type == List.class || type == Set.class){
//                System.out.println(field.getName()+":Collection");
//            }else if(type == Boolean.class || type == boolean.class){
//                System.out.println(field.getName()+":Boolean");
//            }else if(type.isArray()){
//                System.out.println(field.getName()+":Array");
//            }else{
//                System.out.println(field.getName()+":Unknow");
//            }
//        }

    }
}
