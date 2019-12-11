package com.giaynhap.manager;

import com.giaynhap.objects.ThreadCall;
import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ThreadManager {
    private final Logger log = LoggerFactory.getLogger(ThreadManager.class);

    //@Autowired
    private KurentoClient kurento;

    private final ConcurrentMap<String, ThreadCall> rooms = new ConcurrentHashMap<>();


    public ThreadCall getRoom(String roomName) {
        log.debug("Searching for room {}", roomName);
        ThreadCall room = rooms.get(roomName);

        if (room == null) {
            log.debug("Room {} not existent. Will create now!", roomName);
            room = new ThreadCall(roomName, kurento.createMediaPipeline());
            rooms.put(roomName, room);
        }
        log.debug("Room {} found!", roomName);
        return room;
    }

    public void removeRoom(ThreadCall room) {
        this.rooms.remove(room.getName());
        room.close();
        log.info("Room {} removed and closed", room.getName());
    }
}
