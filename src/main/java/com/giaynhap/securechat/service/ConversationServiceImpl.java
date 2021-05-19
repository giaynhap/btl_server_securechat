package com.giaynhap.securechat.service;

import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.model.Conversation;
import com.giaynhap.securechat.model.Message;
import com.giaynhap.securechat.model.UserConversation;
import com.giaynhap.securechat.repository.ConversationRepository;
import com.giaynhap.securechat.repository.MessageRepository;
import com.giaynhap.securechat.repository.UserConversationRepository;
import com.giaynhap.securechat.service.serviceInterface.ConversationService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserConversationRepository userConversationRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Conversation getById(ObjectId id) {
       Optional<Conversation> value = conversationRepository.findById(id);
       if ( value.isPresent() ){
           return value.get();
       }
       return null;
    }

    @Override
    public Conversation getBySenderAndReceiver(String sender, String receiver) {

        //return conversation;
       Aggregation agg = Aggregation.newAggregation(l ->
               new Document("$lookup",
                        new Document("from", mongoTemplate.getCollectionName(UserConversation.class))
                                .append("let", new Document("conversation", new Document("$toObjectId", "$_id")))
                                .append("pipeline",
                                        Arrays.asList(
                                                new Document("$match",
                                                new Document("$expr",
                                                        new Document("$eq", Arrays.asList("$conversation_id", "$$conversation"))))))
                                .append("as", "users")
               )
       ,
               l ->
                       new Document("$match", new Document("$and",
                               Arrays.asList(
                                       new Document("users",
                                        new Document("$elemMatch",
                                               new Document("user.$id", new ObjectId(sender)))
                                       )
                                       ,
                                       new Document("users",
                                       new Document("$elemMatch",
                                               new Document("user.$id", new ObjectId(receiver)))
                                       ),
                                       new Document("is_group",  false )
                               )
                       ))
               );


        AggregationResults<Conversation> results = mongoTemplate.aggregate(agg,
        mongoTemplate.getCollectionName(Conversation.class), Conversation.class);
        Optional<Conversation> conversation =  results.getMappedResults().stream().findFirst();
        if ( !conversation.isPresent() ){
            return null;
        } else {
            return conversation.get();
        }
    }

    @Override
    public List<Message> getMessages(ObjectId id, LocalDateTime lastTime, int limit) {
       // Page<Message> messages = messageRepository.getConversationMessages(id.toHexString(), lastTime, pageRequest);
        Criteria criteria = null;
        if (lastTime == null){
            criteria = Criteria.where("conversation.$id").is( id );
        } else {
            criteria = Criteria.where("conversation.$id").is( id ).and("time").lt(lastTime);
        }
        Query q = Query.query(criteria).limit(limit).with( Sort.by("time").descending());
        return  mongoTemplate.find(q,Message.class);
    }

    @Override
    public Conversation save(Conversation conversation) {
        if (conversation.getUUID() == null){
            conversation.setUUID(new ObjectId());
        }
        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation add(Conversation conversation) {
        if (conversation.getUUID() == null){
            conversation.setUUID(new ObjectId());
        }
        userConversationRepository.saveAll(conversation.getUserConversations());
        return conversationRepository.save(conversation);
    }

    private class NumberOfResults {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }


    @Override
    public Page<Conversation> getPage(String userUuid, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createAt").descending());
        Aggregation agg = Aggregation.newAggregation(l ->
                        new Document("$lookup",
                                new Document("from", mongoTemplate.getCollectionName(UserConversation.class))
                                        .append("let", new Document("conversation", new Document("$toObjectId", "$_id")))
                                        .append("pipeline",
                                                Arrays.asList(
                                                        new Document("$match",
                                                                new Document("$expr",
                                                                        new Document("$eq", Arrays.asList("$conversation_id", "$$conversation"))))))
                                        .append("as", "users")
                        )
                ,
                l ->
                        new Document("$match",   new Document("users",
                                new Document("$elemMatch",
                                        new Document("user.$id", new ObjectId(userUuid)))
                        )
                        ),
                Aggregation.limit(limit),
                Aggregation.skip(page * limit)
        );

        Aggregation count = Aggregation.newAggregation(l ->
                        new Document("$lookup",
                                new Document("from", mongoTemplate.getCollectionName(UserConversation.class))
                                        .append("let", new Document("conversation", new Document("$toObjectId", "$_id")))
                                        .append("pipeline",
                                                Arrays.asList(
                                                        new Document("$match",
                                                                new Document("$expr",
                                                                        new Document("$eq", Arrays.asList("$conversation_id", "$$conversation"))))))
                                        .append("as", "users")
                        )
                ,
                l ->  new Document("$match",   new Document("users",
                                new Document("$elemMatch",
                                        new Document("user.$id", new ObjectId(userUuid)))
                        )
                        ),
                Aggregation.group("_id").count().as("count")
        );

        int countResult = 0;
        Optional<NumberOfResults> aggCount = mongoTemplate.aggregate(count, Conversation.class, NumberOfResults.class).getMappedResults().stream().findFirst();
        if ( aggCount.isPresent() ){
            countResult = aggCount.get().count;
        }

        AggregationResults<Conversation> results = mongoTemplate.aggregate(agg,
                mongoTemplate.getCollectionName(Conversation.class), Conversation.class);
        return new PageImpl<Conversation>(results.getMappedResults(), pageRequest, countResult);
    }

    @Override
    public Message addMessage(Message message) {

        if (message.getUuid() == null) {
            message.setUuid(new ObjectId());
        }

        return  messageRepository.save(message);
    }

    @Override
    public void updateKey(String conversationId, String userId, String Key) {
        Criteria criteria = null;
        criteria = Criteria.where("conversationId").is( new ObjectId(conversationId) ).and("user.$id").is(new ObjectId(userId));

        Query q = Query.query(criteria);
        try {

            Update update = new Update();
            update.set("key", Key);

            mongoTemplate.updateFirst(q,update, UserConversation.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public UserConversation updateUserLastSeen(String conversationId, String userId, LocalDateTime time) {
        Criteria criteria = null;
        criteria = Criteria.where("conversationId").is( new ObjectId(conversationId) ).and("user.$id").is(new ObjectId(userId));

        Query q = Query.query(criteria);
        try {

            Update update = new Update();
            update.set("lastSeen", time);

            mongoTemplate.updateFirst(q,update, UserConversation.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
