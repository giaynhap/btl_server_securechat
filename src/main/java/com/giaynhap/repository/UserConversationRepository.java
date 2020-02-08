package com.giaynhap.repository;

import com.giaynhap.model.Conversation;
import com.giaynhap.model.UserConversation;
import com.giaynhap.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserConversationRepository extends CrudRepository<UserConversation, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM `user_conversation` where `user_uuid` = :uuid1 and `thread_uuid` = :uuid2")
    UserConversation findByUuid(@Param("uuid1") String uuid,@Param("uuid2")String threadUuid);

}
