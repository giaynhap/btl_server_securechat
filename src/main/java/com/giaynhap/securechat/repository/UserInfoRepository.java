package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, ObjectId> {
    @Query("{'name':{'$regex':?0,$options:'i'}}")
    List<UserInfo> findByName(String name);
}
