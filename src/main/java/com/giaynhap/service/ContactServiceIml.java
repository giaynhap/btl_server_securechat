package com.giaynhap.service;

import com.giaynhap.model.Contact;
import com.giaynhap.model.UserInfo;
import com.giaynhap.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ContactServiceIml  implements  ContactService{
    @Autowired
    private ContactRepository contactRepository;
    @Override
    public Page<Contact> getPage(String userUuid, int page, int limit) {

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("create_at").descending());
        return contactRepository
                .findAll(userUuid,pageRequest);
    }
    @Override
    public Contact add(Contact contact) throws Exception{
        if (contactRepository.findByUUID(contact.getUserUuid(),contact.getContactUuid()) != null){
            throw new Exception("Contact existed");
        }
      return  contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) throws Exception{
        Contact dbContact = contactRepository.findById(contact.getId()).get();
        dbContact.setCustomName(contact.getCustomName());
        dbContact.setLevel(dbContact.getLevel());
        return contactRepository.save(dbContact);
    }

    @Override
    public boolean delete(long id) {
        contactRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean delete(String userUuid, String uuid) {
        Contact contact = contactRepository.findByUUID( userUuid, uuid);
        if (contact == null)
            return false;
        contactRepository.delete(contact);
        return true;

    }

    @Override
    public Contact exist(String userUuid, String uuid) {
        return contactRepository.findByUUID(userUuid,uuid);
    }

    @Override
    public Page<Contact> findByName(String name, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("create_at").descending());
        return contactRepository
                .findByName(name,pageRequest);
    }

    @Override
    public List<Contact> getAllContactUserInfo(String uuid) {
        return contactRepository.findAll(uuid);
    }


}
