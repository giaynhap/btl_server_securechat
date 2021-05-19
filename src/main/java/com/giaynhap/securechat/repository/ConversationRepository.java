package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.Conversation;
import com.giaynhap.securechat.model.Device;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, ObjectId> {

    @Query("{'user.$id': ObjectId(?0) , 'isGroup': false  }")
    Conversation getBySenderAndReceiver(String sender, String revc);

}
