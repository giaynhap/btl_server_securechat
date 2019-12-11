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


}
