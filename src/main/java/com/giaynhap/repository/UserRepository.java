package com.giaynhap.repository;

import com.giaynhap.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, String> {
    @Query(value = "SELECT u FROM Users u WHERE u.account = :username AND u.password = :password " )
    Users selectByUserNamePassword(@Param("username") String username,@Param("password") String password);
	
	@Query(value = "SELECT u FROM Users u WHERE u.account = :username")
	Users findByUserName(@Param("username") String username);
	
}