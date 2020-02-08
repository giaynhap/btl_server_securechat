package com.giaynhap.service;

import com.giaynhap.model.UserConversation;
import com.giaynhap.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConversationServiceIml implements UserConversationService {
    @Autowired
    UserConversationRepository userConversationRepository;

    @Override
    public void updateKey(String conversationUuid, String userUuid, String key) {
        UserConversation userConversation = userConversationRepository.findByUuid(userUuid,conversationUuid);
        userConversation.setKey(key);
        userConversationRepository.save(userConversation);
    }

    @Override
    public void updateLastSeen(String conversationUuid, String userUuid, LocalDateTime time) {
        UserConversation userConversation = userConversationRepository.findByUuid(userUuid,conversationUuid);
        userConversation.setLastSeen(time);
        userConversationRepository.save(userConversation);
    }
}
