package com.giaynhap.repository;

import com.giaynhap.model.Contact;
import com.giaynhap.model.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends CrudRepository<Conversation, String> {
    @Query(nativeQuery=true,
            value="select c.* from conversation  c join user_conversation u on c.uuid = u.thread_uuid where u.user_uuid = :uuid",
            countQuery="select count(*) from conversation  c join user_conversation u on c.uuid = u.thread_uuid where u.user_uuid = :uuid")
    Page<Conversation> findAll(@Param("uuid") String userUuid, Pageable page);

    @Query(nativeQuery = true,
        value = "SELECT * FROM `conversation` WHERE UUID = (SELECT thread_uuid AS UUID FROM `user_conversation` WHERE user_uuid = :uuid1 OR user_uuid = :uuid2 " +
                "GROUP BY thread_uuid HAVING COUNT(thread_uuid) = 2 AND (SELECT COUNT(*) FROM  user_conversation a WHERE a.thread_uuid = UUID ) = 2)")
    Conversation getConversationByUser(@Param("uuid1") String uuid,@Param("uuid2")String friendUUid);
}
