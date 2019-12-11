package com.giaynhap;

import com.giaynhap.handler.WebsocketCallHandler;
import com.giaynhap.handler.WebsocketChatHandler;
import com.giaynhap.handler.WebsocketUtilsHandler;
import com.giaynhap.manager.ThreadManager;
import com.giaynhap.manager.UserManager;
import org.kurento.client.KurentoClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableWebSocket
public class RealTimeApp   {

    @Bean
    public UserManager userManager() {
        return new UserManager();
    }


    @Bean
    public ThreadManager threadManager() {
        return new ThreadManager();
    }
 //   @Bean
    public KurentoClient kurentoClient() {
         return KurentoClient.create();
    }
   /*
    @Bean
    public WebsocketChatHandler websocketChatHandler() {
        return new WebsocketChatHandler();
    }
    @Bean
    public WebsocketCallHandler websocketCallHandler() {
        return new WebsocketCallHandler();
    }
    @Bean
    public WebsocketUtilsHandler websocketUtilsHandler() {
        return new WebsocketUtilsHandler();
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketChatHandler(),"/chat").setAllowedOrigins("*");
        registry.addHandler(websocketCallHandler(),"/call").setAllowedOrigins("*");
        registry.addHandler(websocketUtilsHandler(),"/utils").setAllowedOrigins("*");


    }
    */

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(RealTimeApp.class, args);
    }

}
