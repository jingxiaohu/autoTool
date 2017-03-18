package com.jxh.test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")//请求的url,请求的方法是get
        		.accept(MediaType.APPLICATION_JSON_UTF8))//数据的格式
//        		.param("pcode","root")         //添加参数
                .andExpect(status().isOk())   //返回的状态是200
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
    
    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
    	mvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

}