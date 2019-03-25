package com.shopping.miaoshao;

import com.shopping.miaoshao.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MiaoshaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaoApplication.class, args);
    }

    @Bean
    public IdWorker setSnowflakeIdWorker(){
        return new IdWorker();
    }
}
