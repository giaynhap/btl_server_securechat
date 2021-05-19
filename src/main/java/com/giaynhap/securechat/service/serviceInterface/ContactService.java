package com.giaynhap.securechat.service.serviceInterface;

import com.giaynhap.securechat.model.Contact;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    public Page<Contact> getPage(String userUuid, int page, int limit);
    public Contact add(Contact contact);
    public Contact save(Contact contact) ;
    public boolean delete(ObjectId id);
    public boolean delete(String userUuid, String uuid);
    public Contact exist(String userUuid,String uuid);
    public Page<Contact> findByName( String userUuid, String name,int page, int limit);
    public List<Contact> getAllContactUserInfo(String uuid);
}
