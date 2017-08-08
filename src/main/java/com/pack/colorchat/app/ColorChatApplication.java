package com.pack.colorchat.app;

import com.pack.colorchat.impl.ChatServiceKtImpl;
import com.pack.colorchat.rest.ChatControllerKt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {
//        ChatControllerJava.class,
        ChatControllerKt.class,
        ChatServiceKtImpl.class})
@SpringBootApplication
public class ColorChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColorChatApplication.class, args);
    }
}
