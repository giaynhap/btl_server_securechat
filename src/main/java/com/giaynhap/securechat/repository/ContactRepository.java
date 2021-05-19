package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.model.Device;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact, ObjectId> {
    @Query("{'user.$id': ObjectId(?0)}")
    Page<Contact> getContacts(String userUuid , Pageable pageable);
    @Query("{'user.$id': ObjectId(?0)}")
    List<Contact> getContacts(String userUuid );
    @Query("{'user.$id': ObjectId(?0),'contact.$id': ObjectId(?1)}")
    Contact getByUUID(String userUuid, String uuid);
    @Query("{'user.$id': ObjectId(?0), 'contact.$name':{'$regex':?1,$options:'i'} }")
    Page<Contact> findContact(String userUuid , String name , Pageable pageable);
}
