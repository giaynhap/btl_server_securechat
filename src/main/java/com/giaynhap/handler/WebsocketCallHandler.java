package com.giaynhap.handler;

import com.giaynhap.model.Users;
import com.giaynhap.manager.ThreadManager;
import com.giaynhap.manager.UserManager;
import com.giaynhap.objects.ThreadCall;
import com.giaynhap.objects.UserSession;
import com.giaynhap.service.UserServiceIml;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.kurento.client.IceCandidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.UUID;

public class WebsocketCallHandler  extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketCallHandler.class);

    private static final Gson gson = new GsonBuilder().create();

    @Autowired
    private ThreadManager threadManager;

    @Autowired
    private UserManager registry;

    @Autowired
    UserServiceIml service;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);

        final UserSession user = registry.getBySession(session);
        UserSession sender;
        if (user != null) {
            log.debug("Incoming message from user '{}': {}", user.getName(), jsonMessage);
        } else {
            log.debug("Incoming message from new user: {}", jsonMessage);
        }

        switch (jsonMessage.get("id").getAsString()) {
            case "joinRoom":
                joinRoom(jsonMessage, session);
                break;
            case "receiveVideoFrom":
                final String senderName = jsonMessage.get("sender").getAsString();
                sender = registry.getByUUID(senderName);
                final String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
                user.receiveVideoFrom(sender, sdpOffer);
                break;
            case "leaveRoom":
                leaveRoom(user);
                break;
            case "onIceCandidate":
                JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();
                if (user != null) {
                    IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(),
                            candidate.get("sdpMid").getAsString(), candidate.get("sdpMLineIndex").getAsInt());
                    sender = registry.getByUUID(jsonMessage.get("uuid").getAsString());
                    user.addCandidate(cand,sender.getInfo() );
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        UserSession user = registry.removeBySession(session);
        if (user == null)
            return;
        if ( user.thread != null )
        user.thread.leave(user);
    }

    private void joinRoom(JsonObject params, WebSocketSession session) throws IOException {
        final String roomName = params.get("room").getAsString();
        final String name = params.get("name").getAsString();
        log.info("PARTICIPANT {}: trying to join room {}", name, roomName);
        Users u = new Users();
        u.setUUID(UUID.randomUUID().toString());
        u.setName(name);
        JsonObject response = new JsonObject();
        response.addProperty("id","setUserUUID");
        response.addProperty("uuid",u.getUUID());
        session.sendMessage(new TextMessage(response.toString()));

        ThreadCall room = threadManager.getRoom(roomName);
        final UserSession user = room.join(u, session);

        registry.register(user);

    }

    private void leaveRoom(UserSession user) throws IOException {
        final ThreadCall room = user.thread;
        room.leave(user);
        if (room.getParticipants().isEmpty()) {
            threadManager.removeRoom(room);
        }
    }
}
