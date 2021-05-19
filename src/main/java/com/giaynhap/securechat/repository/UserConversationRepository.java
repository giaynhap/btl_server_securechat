package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.UserConversation;
import com.giaynhap.securechat.model.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface UserConversationRepository extends MongoRepository<UserConversation, ObjectId> {
}
