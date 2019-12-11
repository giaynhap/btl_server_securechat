package com.giaynhap.repository;

import com.giaynhap.model.Conversation;
import com.giaynhap.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MessageRepository  extends CrudRepository<Message, String> {
    @Query(nativeQuery=true,
            value="select m.* from messages m where m.user_uuid = :useruuid and m.thread_uuid = :threaduuid and time < :time  order by time desc limit 20 ",
            countQuery="select m.* from messages m where m.user_uuid = :useruuid and m.thread_uuid = :threaduuid and time < :time order by time desc limit 20")
    List<Message> getMessage(@Param("useruuid") String userUuid, @Param("threaduuid")  String threadUuid,@Param("time")  LocalDateTime lastTime);
    @Query(nativeQuery=true,
            value="select m.* from messages m where m.user_uuid = :useruuid and m.thread_uuid = :threaduuid  order by time desc limit 20 ",
            countQuery="select m.* from messages m where m.user_uuid = :useruuid and m.thread_uuid = :threaduuid order by time desc limit 20")
    List<Message> getMessage(@Param("useruuid") String userUuid, @Param("threaduuid")  String threadUuid);


}
