package com.hlife;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }

    @GetMapping("/")
    public String home() {
        /*HproseClient client = new HproseHttpClient();
        client.useService("http://127.0.0.1:30000/");

        RpcService service = client.useService(RpcService.class);
        JSONArray jsonArray = new JSONArray()
                .fluentAdd(
                        new JSONObject().fluentPut("name", "a")
                            .fluentPut("value", "a")
                ).fluentAdd(
                        new JSONObject().fluentPut("name", "b")
                                .fluentPut("value", "b")
                ).fluentAdd(
                        new JSONObject().fluentPut("name", "c")
                                .fluentPut("value", "c")
                ).fluentAdd(
                        new JSONObject().fluentPut("name", "d")
                                .fluentPut("value", "d")
                );
        JSONArray content = service.GetTjspByLabels(jsonArray);
        log.info("content: {}", content);*/
        return "hello spring boot";
    }
}
