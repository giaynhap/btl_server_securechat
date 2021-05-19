package com.giaynhap.securechat.service;

import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.repository.ContactRepository;
import com.giaynhap.securechat.service.serviceInterface.ContactService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;
    @Override
    public Page<Contact> getPage(String userUuid, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createAt").descending());
        return contactRepository.getContacts(userUuid,pageRequest);
    }

    @Override
    public Contact add(Contact contact)  {
        return contactRepository.save(contact);
    }

    @Override
    public Contact save(Contact contact)  {
        return contactRepository.save(contact);
    }

    @Override
    public boolean delete(ObjectId id) {
         contactRepository.deleteById(id);
         return true;
    }

    @Override
    public boolean delete(String userUuid, String uuid) {
        Contact contact = contactRepository.getByUUID(userUuid, uuid);
        if ( contact == null ){
            return false;
        }
        contactRepository.delete(contact);
        return true;
    }

    @Override
    public Contact exist(String userUuid, String uuid) {
        return contactRepository.getByUUID(userUuid,uuid);
    }

    @Override
    public Page<Contact> findByName(String userUuid, String name, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createAt").descending());
        return contactRepository.findContact(userUuid, name, pageRequest);
    }


    @Override
    public List<Contact> getAllContactUserInfo(String uuid) {
        return contactRepository.getContacts(uuid);
    }
}
