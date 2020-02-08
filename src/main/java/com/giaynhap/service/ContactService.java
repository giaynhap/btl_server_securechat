package com.giaynhap.service;

import com.giaynhap.model.Contact;
import com.giaynhap.model.UserInfo;
import com.giaynhap.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public
interface ContactService {
    public Page<Contact> getPage( String userUuid,int page,   int limit);
    public Contact add(Contact contact) throws Exception;
    public Contact update(Contact contact) throws Exception;
    public boolean delete(long id);
    public boolean delete(String userUuid, String uuid);
    public Contact exist(String userUuid,String uuid);
    public Page<Contact> findByName( String userUuid,int page, int limit);

    public List<Contact> getAllContactUserInfo(String uuid);
}
