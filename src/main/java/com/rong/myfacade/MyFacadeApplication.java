package com.rong.myfacade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@MapperScan("com.rong.myfacade.mapper")
public class MyFacadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFacadeApplication.class, args);
    }

}
