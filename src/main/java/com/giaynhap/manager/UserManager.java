package com.giaynhap.manager;

import com.giaynhap.objects.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private final ConcurrentHashMap<String, UserSession> usersByUUID = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UserSession> usersBySessionId = new ConcurrentHashMap<>();

    public void register(UserSession user) {
        usersByUUID.put(user.getUUID(), user);
        usersBySessionId.put(user.getSession().getId(), user);
    }
    public UserSession getByUUID(String uuid) {
        return usersByUUID.get(uuid);
    }
    public UserSession getBySession(WebSocketSession session) {
        return usersBySessionId.get(session.getId());
    }
    public boolean exists(String uuid) {
        return usersByUUID.keySet().contains(uuid);
    }
    public UserSession removeBySession(WebSocketSession session) {
        final UserSession user = getBySession(session);
        if (user == null )
            return null;
        usersByUUID.remove(user.getUUID());
        usersBySessionId.remove(session.getId());
        return user;
    }
}
