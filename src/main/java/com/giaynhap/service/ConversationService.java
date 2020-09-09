package com.giaynhap.service;

import com.giaynhap.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public
interface ConversationService {
    public Page<Conversation> getPage(String userUuid, int page, int limit);
    public Conversation add (Conversation conversation);
    public Conversation update (Conversation conversation);
    public UserConversation addUser(String conversationUuid, String userUuid);
    public Conversation get(String uuid);
    public List<Message> getMessage(String conversationuUuid, String userUuid, LocalDateTime from);
    public Conversation getUserConversation(String uuid1,String uuid2);
    public List<UserInfo> getListUser(String uuid);
    public Message addMessage(Message mesage);
    public void addUser(String conversationUuid,String userUuid, String key);
}
