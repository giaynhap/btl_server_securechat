package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.Device;
import com.giaynhap.securechat.model.Message;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    @Query("{'conversation.$id': ObjectId(?0)")
    Page<Message> getConversationMessages(String conversationId, LocalDateTime time, Pageable pageable);
}
