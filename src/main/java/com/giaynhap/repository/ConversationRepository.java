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
            value="SELECT  c.uuid,c.user_uuid,c.thread_name,c.create_at,c.last_msg_at, (\n" +
                    "SELECT COUNT(*) FROM messages WHERE user_uuid = :uuid\n" +
                    "AND thread_uuid = c.uuid AND sender_uuid <> user_uuid AND  `time` > \n" +
                    "(SELECT last_seen FROM user_conversation WHERE user_uuid = :uuid AND thread_uuid = c.uuid)\n" +
                    " ) AS unread FROM conversation  c JOIN user_conversation u ON c.uuid = u.thread_uuid WHERE u.user_uuid = :uuid\n",
            countQuery="select count(*) from conversation  c join user_conversation u on c.uuid = u.thread_uuid where u.user_uuid = :uuid")
    Page<Conversation> findAll(@Param("uuid") String userUuid, Pageable page);

    @Query(nativeQuery = true,
        value = "SELECT * FROM `conversation` WHERE UUID in (SELECT thread_uuid AS UUID FROM `user_conversation` WHERE user_uuid = :uuid1 OR user_uuid = :uuid2 " +
                "GROUP BY thread_uuid HAVING COUNT(thread_uuid) = 2 AND (SELECT COUNT(*) FROM  user_conversation a WHERE a.thread_uuid = UUID ) = 2) LIMIT 1")
    Conversation getConversationByUser(@Param("uuid1") String uuid,@Param("uuid2")String friendUUid);
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) FROM messages WHERE user_uuid = :uuid1 AND thread_uuid = :uuid2 AND sender_uuid <> user_uuid AND  `time` > (SELECT last_seen FROM user_conversation WHERE user_uuid = :uuid1 AND thread_uuid = :uuid2)")
    int countUnread(@Param("uuid1") String uuid,@Param("uuid2")String threadUuid);

}
