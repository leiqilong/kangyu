package com.hlife;


import com.hlife.framework.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@MapperScan("com.hlife.task_manager.dao")
public class KangyuTaskManagerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(KangyuTaskManagerApplication.class, args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }

    @RequestMapping("/")
    public String hello() {
        return "Hello Spring Boot";
    }
}
