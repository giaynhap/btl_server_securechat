package com.giaynhap.repository;

import com.giaynhap.model.Contact;
import com.giaynhap.model.Conversation;
import com.giaynhap.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends CrudRepository<Contact, Long> {
    @Query(nativeQuery=true,
            value="select * from contact where user_uuid = :user_uuid",
            countQuery="SELECT count(*) FROM contact where user_uuid = :user_uuid")
    Page<Contact> findAll(@Param("user_uuid") String userUuid,Pageable page);

    @Query(nativeQuery=true,
            value = "SELECT * FROM contact c WHERE c.user_uuid = :myuuid and c.contact_uuid = :uuid limit 1" )
    Contact findByUUID(@Param("myuuid") String userUuid,@Param("uuid") String uuid);

    @Query(nativeQuery = true,
    value = "SELECT c.* FROM contact c join user_info u on c.contact_uuid = u.uuid where u.name like  CONCAT('%',:name,'%') ",
            countQuery="SELECT count(*) FROM contact c join user_info u on c.contact_uuid = u.uuid where u.name like  CONCAT('%',:name,'%') ")
    Page<Contact> findByName(@Param("name") String name, Pageable page);

}
