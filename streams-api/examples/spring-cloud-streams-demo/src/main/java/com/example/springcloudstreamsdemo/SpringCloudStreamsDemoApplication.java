package com.example.springcloudstreamsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class SpringCloudStreamsDemoApplication {

    @Bean
    public Function<String,String> message(){ // message-in-0 , message-out-0
        return message->{
            System.out.println("Received Message: "+message);
            return message.toUpperCase();
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamsDemoApplication.class, args);
    }

}
