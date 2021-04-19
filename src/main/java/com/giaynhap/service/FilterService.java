package com.giaynhap.service;

import com.giaynhap.model.DTO.ConversationDTO;
import com.giaynhap.model.DTO.MessageDTO;
import com.giaynhap.model.DTO.SocketMessageCommandDTO;
import com.giaynhap.model.Message;
import com.giaynhap.model.UserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class FilterService implements Runnable {
    public BlockingQueue<SocketMessageCommandDTO> msgQueue = new LinkedBlockingQueue<>();
    Thread filterThread = null;
    boolean isRunning = false;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private SocketService socketService;
    @Autowired
    private ConversationServiceIml conversationService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void startThread() {
        try {
            socketService.startConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (filterThread != null) {
//            thread.stop();
            filterThread = null;
        }
        isRunning = true;
        filterThread = new Thread(this, "FilterModule");
        filterThread.start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (filterThread == thisThread && isRunning) {
            MessageDTO msg = null;
            SocketMessageCommandDTO socketMsg = null;
            try {
                socketMsg = msgQueue.take();
                msg = socketMsg.getData();
                System.out.println("\n\n\n\n Get MSG: " + msg.getPayload() + "\n\n\n\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            assert msg != null;
            List<UserInfo> users = conversationService.getListUser(msg.getThreadUuid());
            try {
                String filteredMsg = socketService.sendMessage(msg.getPayload());
                System.out.println("\n\n\n\n Filtered MSG: " + filteredMsg + "\n\n\n\n");
                msg.setPayload(filteredMsg);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    msgQueue.put(socketMsg);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                try {
                    socketService.startConnection();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            MessageDTO finalMsg = msg;
            SocketMessageCommandDTO finalSocketMsg = socketMsg;
            users.forEach(m -> {
                String threadName = ConversationDTO.createName(users, m.getUUID());
                finalMsg.setThreadName(threadName);
                messagingTemplate.convertAndSend("/topic/" + m.getUUID(), finalSocketMsg);

                Message message = finalMsg.toEntity(modelMapper);

                message.setUserUuid(m.getUUID());
                message.setEncrypt(true);

                conversationService.addMessage(message);
            });
        }
    }
}
