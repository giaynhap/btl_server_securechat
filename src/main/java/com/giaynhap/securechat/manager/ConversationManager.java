package com.giaynhap.securechat.manager;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.*;
import com.giaynhap.securechat.model.response.DTO.ConversationDTO;
import com.giaynhap.securechat.model.response.DTO.MessageDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversationManager extends BaseManager {

    public ConversationDTO getConversation(String uuid){

        try {
            Object cacheConversation = redisTemplate.opsForHash().get(uuid, AppConstant.CacheKeys.CONVERSATION);
            if (cacheConversation != null && cacheConversation instanceof ConversationDTO) {
                return (ConversationDTO) cacheConversation;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ConversationDTO dto = ConversationDTO.fromEntity(modelMapper,conversationService.getById(new ObjectId(uuid)));
        redisTemplate.opsForHash().put(uuid, AppConstant.CacheKeys.CONVERSATION, dto);
       return dto;
    }

    public void addMessage(MessageDTO message){

        Message messageEntry = message.toEntity(modelMapper);
        conversationService.addMessage(messageEntry);
    }

    public void updateLastSeen(String conversationUUID, String user, LocalDateTime now){
       Conversation conversation =  conversationService.getById(new ObjectId(conversationUUID));
       conversation.setLastMessageAt(now);
       conversationService.save(conversation);
       ConversationDTO dto = ConversationDTO.fromEntity(modelMapper,conversation);
       redisTemplate.opsForHash().put(conversationUUID, AppConstant.CacheKeys.CONVERSATION, dto);
       conversationService.updateUserLastSeen(conversationUUID, user,now);
    }

    public Page<ConversationDTO> getPage(String uuid, int page, int limit){
        return conversationService.getPage(uuid,page,limit).map( m -> {
           ConversationDTO dto = ConversationDTO.fromEntity(modelMapper, m);
            dto.setName(ConversationDTO.createName(dto.getUsers(), uuid));
           return dto;
        });
    }

    public ConversationDTO getConversation( String sender, String receiver){
        Conversation conversation = conversationService.getBySenderAndReceiver(sender,receiver);
        return ConversationDTO.fromEntity(modelMapper, conversation);
    }

    public ConversationDTO createConversation(String sender, List<String> users){

        if (users == null){
            users = new ArrayList<>();
        }

        if (!users.contains(sender)){
            users.add(sender);
        }
        List<UserConversation> userConversations = new ArrayList<>();
        Conversation conversation = new Conversation();
        conversation.setUUID( new ObjectId());
        conversation.setGroup(false);
        UserInfo senderInfo = new UserInfo();
        senderInfo.setId(new ObjectId(sender));

        for (String uuid : users){
            UserConversation uc = new UserConversation();
            uc.setUser(new UserInfo(new ObjectId(uuid)));

            uc.setLastSeen(LocalDateTime.now());
            uc.setId(new ObjectId());
            uc.setConversationId(conversation.getUUID());
            userConversations.add(uc);
        }
        conversation.setUser(senderInfo);
        conversation.setUserConversations(userConversations);
        conversation =  conversationService.add(conversation);
       return ConversationDTO.fromEntity(modelMapper,conversation);
    }

    public List<MessageDTO> getConversationMessages(String conversation, LocalDateTime time, int limit){
        List<Message> messages = conversationService.getMessages(new ObjectId(conversation),time, limit);
        return messages.stream().map( m -> MessageDTO.fromEntity(modelMapper,m)).collect(Collectors.toList());
    }

    public Boolean updateKey(String conversationId, String userId, String key){
       // conversationService.updateKey(conversationId,userId,key);
        return  false;
    }
}
