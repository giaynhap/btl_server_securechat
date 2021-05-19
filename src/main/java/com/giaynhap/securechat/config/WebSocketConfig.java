package com.giaynhap.securechat.config;


import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.service.serviceInterface.UserService;
import com.giaynhap.securechat.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    /*@Autowired
    UserOnlineController onlineController;
*/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //  registry.setApplicationDestinationPrefixes("/queue");
        registry.enableSimpleBroker("/topic","/qr-login");
        //  registry.enableSimpleBroker("/qr-login");
    }
    public Boolean isPublicTopic(String destination){
        if (destination.startsWith("/qr-login")){
            return true;
        }
        return false;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                if (accessor.getCommand()== null){
                    return message;
                }

                List<String> tokenList = accessor.getNativeHeader("X-Authorization");
                accessor.removeNativeHeader("X-Authorization");

                String requestTokenHeader = null;
                String username = null;
                String jwtToken = null;
                try {
                    MessageHeaders headers = message.getHeaders();

                    MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS,MultiValueMap.class);
                    for(Map.Entry<String, List<String>> head : multiValueMap.entrySet())
                    {
                        System.out.println(head.getKey() + "#" + head.getValue());
                        if (head.getValue() != null && !head.getValue().isEmpty()) {
                            if (head.getKey().equals("X-Authorization")) {
                                requestTokenHeader = head.getValue().get(0);
                            }
                        }
                    }
                    jwtToken = requestTokenHeader ;
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    System.out.println("WEBSOCKET Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    System.out.println("WEBSOCKET JWT Token has expired");
                } catch (Exception e){

                    System.out.println("WEBSOCKET "+e.toString()+ " "+ accessor.getCommand());
                    username = null;
                }
                String destination = accessor.getDestination();
                System.out.println("["+accessor.getCommand()+"]"+accessor.getDestination());

                if (username != null) {

                    if (jwtTokenUtil.validateToken(jwtToken)) {
                        UserInfoDTO userDetails = userService.getUserCache(username);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, new ArrayList<>());
                        accessor.setUser(usernamePasswordAuthenticationToken);
                    }
                }
                else
                {

                    if ( destination != null && !isPublicTopic(destination ) && StompCommand.SUBSCRIBE.equals(accessor.getCommand()))
                        throw new IllegalArgumentException("No permission for this topic");
                    else {
                        accessor.setLeaveMutable(true);
                        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
                    }
                }
                if ( destination != null  && username != null ) {
                    if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                     //   onlineController.addUser(username);
                        accessor.getSessionAttributes().put("user_uuid", username);
                    }
                }
                accessor.setLeaveMutable(true);
                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());

            }
        });
    }

}
