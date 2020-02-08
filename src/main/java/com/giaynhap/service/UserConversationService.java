package com.giaynhap.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface UserConversationService {
    public void updateKey(String conversationUuid,String userUuid,String key);
    public void updateLastSeen(String conversationUuid, String userUuid, LocalDateTime time);
}
