package com.giaynhap.service;

import com.giaynhap.model.*;
import com.giaynhap.repository.ConversationRepository;
import com.giaynhap.repository.MessageRepository;
import com.giaynhap.repository.UserConversationRepository;
import com.giaynhap.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class ConversationServiceIml  implements ConversationService{
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    UserConversationRepository userConversationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserInfoRepository userInfoRepository;


    @Override
    public Page<Conversation> getPage(String userUuid, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("last_msg_at").descending());
        Page<Conversation> pages =  conversationRepository.findAll(userUuid,pageRequest);
       /* pages.map(conversation -> {
            conversation.setUnread(conversationRepository.countUnread(conversation.getUser_uuid(),conversation.getUUID()));
            return conversation;
        });*/
        return pages;
    }

    @Override
    public Conversation add(Conversation conversation) {
        conversation.setUUID(UUID.randomUUID().toString());


        conversation = conversationRepository.save(conversation);

        UserConversation userConv = new UserConversation();
        userConv.setUserUuid(conversation.getUser_uuid());
        userConv.setThreadUuid(conversation.getUUID());
        userConversationRepository.save(userConv);


        return conversation;
    }

    @Override
    public Conversation update(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public UserConversation addUser(String conversationUuid, String userUuid) {
        UserConversation userConv = new UserConversation();
        userConv.setUserUuid(userUuid);
        userConv.setThreadUuid(conversationUuid );
        return userConversationRepository.save(userConv);
    }

    @Override
    public Conversation get(String uuid) {
        return conversationRepository.findById(uuid).get();
    }

    @Override
    public List<Message> getMessage(String conversationuUuid, String userUuid, LocalDateTime from) {
        if (from == null)
            return messageRepository.getMessage(userUuid,conversationuUuid);

        return messageRepository.getMessage(userUuid,conversationuUuid,from);
    }

    @Override
    public Conversation getUserConversation(String uuid1, String uuid2) {

        return conversationRepository.getConversationByUser(uuid1,uuid2);
    }

    @Override
    public List<UserInfo> getListUser(String uuid) {
        return userInfoRepository.getListUserConversation(uuid);
    }

    @Override
    public Message addMessage(Message mesage) {
        mesage.setUuid(UUID.randomUUID().toString());
        return messageRepository.save(mesage);
    }


}
