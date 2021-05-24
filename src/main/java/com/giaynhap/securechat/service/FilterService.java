package com.giaynhap.securechat.service;

import com.giaynhap.securechat.model.response.DTO.SocketMessageCommandDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class FilterService  implements  SocketService.SocketMessageEvent {
    public HashMap<Long,SocketMessageCommandDTO> messages = new HashMap<Long,SocketMessageCommandDTO>();
    Thread filterThread = null;
    boolean isRunning = false;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private SocketService socketService;
    @Autowired
    CWebsocketService cWebsocketService;


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void config(){
        socketService.setListener( this );
    }


    public void addMessage(SocketMessageCommandDTO message) {
        SocketService.MessageFilter m = new SocketService.MessageFilter(message.getData().getUuid(), message.getData().getPayload());
        messages.put(m.id, message);
        socketService.sendMessageQueue(m);
    }

    @Override
    public void onMessasge(SocketService.MessageFilter message) {
        if (!messages.containsKey(message.id)){
            // warning
            return;
        }

        SocketMessageCommandDTO m = messages.get(message.id);
        m.getData().setPayload(message.message);
        m.getData().setUuid(message.getMessageId());
        cWebsocketService.broadcastMessage(m);
    }

    @Override
    public void onBlockMessage(SocketService.MessageFilter message) {
        // truong hop tin trong hang cho
        for(Map.Entry<Long,SocketMessageCommandDTO> entry : messages.entrySet()) {
           if (entry.getValue().getData().getUuid().equals(message.getMessageId())) {
               messages.remove(entry.getKey());
               return;
            }
        }

        // truong hop tin da gui di
        cWebsocketService.blockMessage(message.getMessageId());

    }

    @Override
    public void onTimeoutMessage(SocketService.MessageFilter message) {
        if (!messages.containsKey(message.id)){
            // warning
            return;
        }
        SocketMessageCommandDTO m = messages.get(message.id);
        m.getData().setUuid(message.getMessageId());
        cWebsocketService.broadcastMessage(m);
    }
}
