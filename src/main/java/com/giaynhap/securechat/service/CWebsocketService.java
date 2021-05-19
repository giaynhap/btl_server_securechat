package com.giaynhap.securechat.service;

import com.giaynhap.securechat.manager.ConversationManager;
import com.giaynhap.securechat.model.response.DTO.ConversationDTO;
import com.giaynhap.securechat.model.response.DTO.SocketMessageCommandDTO;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CWebsocketService {

    @Autowired
    ConversationManager conversationManager;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void broadcastMessage(SocketMessageCommandDTO message){
        ConversationDTO conversation  =  conversationManager.getConversation(message.getData().getThreadUuid());
        if (conversation == null || conversation.getUsers() == null){
            return;
        }

        List<UserInfoDTO> users = conversation.getUsers();
        UserInfoDTO sender = message.getData().getSender();

        if (users == null || sender == null)
            return;

        message.getData().setSender(sender);
        message.getData().setUuid(new ObjectId().toHexString());

        users.forEach(m->{

            System.out.println("sendto "+m.getName());
            String threadName = ConversationDTO.createName(users,m.getId());
            message.getData().setThreadName(threadName);
            messagingTemplate.convertAndSend("/topic/"+m.getId(),message);
           /* Message msg = message.getData().toEntity(modelMapper );
            msg.setSender(m);
            msg.setEncrypt(true);
            conversationService.addMessage(msg);*/

        });
        conversationManager.addMessage(message.getData());
    }
    public void broadcastSendStatus(String uuid ,SocketMessageCommandDTO message){
        ConversationDTO conversation  =  conversationManager.getConversation(message.getData().getThreadUuid());
        if (conversation == null || conversation.getUsers() == null){
            return;
        }

        List<UserInfoDTO> users = conversation.getUsers();
        if (users == null)
            return;
        users.forEach(m->{
            if (!m.getId().equals(uuid))
                messagingTemplate.convertAndSend("/topic/"+m.getId(),message);
        });
    }
}
