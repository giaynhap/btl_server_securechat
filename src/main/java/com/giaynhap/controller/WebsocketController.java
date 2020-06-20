package com.giaynhap.controller;

import com.giaynhap.config.AppConstant;
import com.giaynhap.model.Conversation;
import com.giaynhap.model.DTO.*;
import com.giaynhap.model.Message;
import com.giaynhap.model.UserInfo;
import com.giaynhap.service.ConversationService;
import com.giaynhap.service.ConversationServiceIml;
import com.giaynhap.service.UserConversationServiceIml;
import com.giaynhap.service.UserServiceIml;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

import static com.giaynhap.config.AppConstant.MessageCommand.MESSAGE;
import static com.giaynhap.config.AppConstant.MessageCommand.READ;

@Controller
public class WebsocketController {
    @Autowired
    private UserServiceIml userService;
    @Autowired
    private ConversationServiceIml conversationService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserConversationServiceIml userConversationService;

    @Autowired
    ModelMapper modelMapper;

    @MessageMapping("/send")
    public void sendMessage(@Payload SocketMessageCommandDTO message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        if ( message== null){
            return ;
        }
        UserDetails user = (UserDetails)((UsernamePasswordAuthenticationToken)headerAccessor.getUser()).getPrincipal();
        Message msg;
        headerAccessor.getSessionAttributes().put("user_uuid", user.getUsername());

        switch (message.getCommand()){
            case MESSAGE:
                msg = message.getData().toEntity(modelMapper );
                msg.setSenderUuid(user.getUsername());
                msg.setTime(LocalDateTime.now());
                Conversation conversation = conversationService.get(msg.getThreadUuid());
                conversation.setLastMessageAt(LocalDateTime.now());
                conversationService.update(conversation);

                if (msg != null) {
                    msg.setConversation(conversation);
                    message.setData(MessageDTO.fromEntity(modelMapper, msg));
                    broadcastMessage(message);
                }
                break;
            case TYPING:
            case READ:
                msg = message.getData().toEntity(modelMapper );
                msg.setSenderUuid(user.getUsername());
                msg.setTime(LocalDateTime.now());
                message.setData(MessageDTO.fromEntity(modelMapper, msg));
                broadcastSendStatus(user.getUsername(),message);
                if (message.getCommand() == READ){
                    userConversationService.updateLastSeen(msg.getThreadUuid(),user.getUsername(),LocalDateTime.now());
                }
                break;

        }

    }
    private void broadcastMessage(SocketMessageCommandDTO message){
        List<UserInfo> users = conversationService.getListUser(message.getData().getThreadUuid());
        UserInfo sender = userService.getUserInfo(message.getData().getSenderUuid());
        message.getData().setSender(UserInfoDTO.fromEntity(modelMapper,sender));

        if (users == null)
            return;

        users.forEach(m->{

            System.out.println("sendto "+m.getName());
            String threadName = ConversationDTO.createName(users,m.getUUID());
            message.getData().setThreadName(threadName);
            messagingTemplate.convertAndSend("/topic/"+m.getUUID(),message);
            Message msg = message.getData().toEntity(modelMapper );

            msg.setUserUuid(m.getUUID());
            msg.setEncrypt(true);

            conversationService.addMessage(msg);
        });
    }
    private void broadcastSendStatus(String uuid ,SocketMessageCommandDTO message){
        List<UserInfo> users = conversationService.getListUser(message.getData().getThreadUuid());
        if (users == null)
            return;

        users.forEach(m->{
            if (!m.getUUID().equals(uuid))
            messagingTemplate.convertAndSend("/topic/"+m.getUUID(),message);
        });
    }

}
