package com.hlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @EnableScheduling
@EnableSwagger2
//@EnableAutoConfiguration
@RestController
//@EnableTransactionManagement
@SpringBootApplication
//@Slf4j
public class ServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }

    @GetMapping("/")
    public String home() {
        return "hello spring boot";
    }
}