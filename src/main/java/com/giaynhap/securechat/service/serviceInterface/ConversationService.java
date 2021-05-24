package com.giaynhap.securechat.service.serviceInterface;

import com.giaynhap.securechat.model.Conversation;
import com.giaynhap.securechat.model.Message;
import com.giaynhap.securechat.model.UserConversation;
import com.giaynhap.securechat.model.response.DTO.MessageDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ConversationService {
    Conversation getById(ObjectId id);
    Conversation getBySenderAndReceiver(String sender, String receiver);
    List<Message> getMessages(ObjectId id, LocalDateTime lastTime, int limit);
    Conversation save(Conversation conversation);
    Conversation add(Conversation conversation);
    Page<Conversation> getPage(String userUuid, int page, int limit);
    void updateKey(String conversationId, String userId, String Key);
    Message addMessage(Message message);
    UserConversation updateUserLastSeen(String conversationId, String userId, LocalDateTime time);
    void removeMessage(String messageId);
    Message getMessage(String messageId);
}
