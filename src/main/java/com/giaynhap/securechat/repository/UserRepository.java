package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    @Query("{ 'account' : ?0 }")
    User findByUserName( String username);
}
