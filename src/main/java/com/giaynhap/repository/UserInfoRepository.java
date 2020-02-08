package com.giaynhap.repository;

import com.giaynhap.model.UserInfo;
import com.giaynhap.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {

    @Query(value = "select * from user_info where name like CONCAT('%',:name,'%')",
            countQuery="select count(*) from user_info where name like CONCAT('%',:name,'%')  ",
            nativeQuery = true)
    Page<UserInfo> findByName( @Param("name") String name, Pageable page);




    @Query(value = "select u.* from user_conversation c join user_info u on u.uuid = c.user_uuid where c.thread_uuid = :uuid",
            nativeQuery = true)
    public List<UserInfo> getListUserConversation(@Param("uuid") String uuid);

    @Query(
            value="SELECT * FROM `user_info` where uuid <> :uuid ORDER BY RAND() LIMIT 10",
            nativeQuery = true
    )
    public List<UserInfo> getRandomSuggest(@Param("uuid") String uuid);
}
