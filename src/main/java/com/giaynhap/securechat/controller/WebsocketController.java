package com.giaynhap.securechat.controller;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.response.DTO.ConversationDTO;
import com.giaynhap.securechat.model.response.DTO.MessageDTO;
import com.giaynhap.securechat.model.response.DTO.SocketMessageCommandDTO;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.service.CWebsocketService;
import com.giaynhap.securechat.service.FilterService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebsocketController extends  BaseController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private CWebsocketService cWebsocketService;

    @Autowired
    private FilterService filterService;

    @MessageMapping("/send")
    public void sendMessage(@Payload SocketMessageCommandDTO message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        if ( message== null){
            return ;
        }
        UserInfoDTO user = (UserInfoDTO)((UsernamePasswordAuthenticationToken)headerAccessor.getUser()).getPrincipal();
        MessageDTO msg = null;
        headerAccessor.getSessionAttributes().put("user_uuid", user.getId());
        switch (message.getCommand()){
            case MESSAGE:
                msg = message.getData();
                msg.setSender(user);
                msg.setTime(LocalDateTime.now());
                if (msg != null) {
                    message.setData(msg);
                    if (message.getData().getEncrypt()) {
                        cWebsocketService.broadcastMessage(message);
                    } else {
                        filterService.addMessage(message);
                    }
                }
                break;
            case TYPING:
            case READ:
                msg = message.getData();
                msg.setSender(user);
                msg.setTime(LocalDateTime.now());
                message.setData(msg);
                cWebsocketService.broadcastSendStatus(user.getId(),message);
                if (message.getCommand() == AppConstant.MessageCommand.READ){
                    conversationManager.updateLastSeen(msg.getThreadUuid(),user.getId(),LocalDateTime.now());
                }

                break;
        }

    }

}
