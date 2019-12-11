package com.giaynhap.handler;

import com.giaynhap.manager.ThreadManager;
import com.giaynhap.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebsocketChatHandler extends TextWebSocketHandler {

    @Autowired
    UserManager manager;
    @Autowired
    ThreadManager thread;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {



    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}
