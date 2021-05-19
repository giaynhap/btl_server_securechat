package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.UserKey;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserKeyRepository extends MongoRepository<UserKey, ObjectId> {
}
