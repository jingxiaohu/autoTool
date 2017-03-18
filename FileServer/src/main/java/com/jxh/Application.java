package com.jxh;
/**
 * spring 运行入口
 * @author jingxiaohu
 *
 */
import java.util.Arrays;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize("2048KB");  
        factory.setMaxRequestSize("2048KB");  
        return factory.createMultipartConfig();  
    }
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				// TODO Auto-generated method stub

		            System.out.println("Let's inspect the beans provided by Spring Boot:");

		            String[] beanNames = ctx.getBeanDefinitionNames();
		            Arrays.sort(beanNames);
		            for (String beanName : beanNames) {
		                System.out.println(beanName);
		            }
		     };
        };
    }
}