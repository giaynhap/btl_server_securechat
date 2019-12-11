package com.giaynhap.repository;

import com.giaynhap.model.UserInfo;
import com.giaynhap.model.UserKey;
import org.springframework.data.repository.CrudRepository;

public interface UserKeyRepository extends CrudRepository<UserKey, String> {

}
