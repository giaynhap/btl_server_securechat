package com.giaynhap.securechat.manager;

import com.giaynhap.securechat.exception.ApiException;
import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.model.response.DTO.ContactDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Component
public class ContactManager extends BaseManager {

   public Page<ContactDTO> getPageContact(String userUUID, int page, int limit){
       Page<Contact> contacts = contactService.getPage(userUUID, page, limit);
       return contacts.map(m -> ContactDTO.fromEntity(modelMapper, m));
   }

   public ContactDTO addContact(String userId,ContactDTO data) throws  ApiException {
       UserInfo user = userService.getUserInfo(userId);
       UserInfo contact = userService.getUserInfo(data.getContactUuid());
       Contact entity = data.toEntity(modelMapper);
       entity.setCreateAt(LocalDateTime.now());
       entity.setUser(user);
       entity.setContact(contact);
       entity.setId(new ObjectId());
       try {
           entity = contactService.add(entity);
       } catch (Exception e){

       }
       return ContactDTO.fromEntity(modelMapper, entity);
   }

    public ContactDTO updateContactName(String userId, ContactDTO contact) throws ApiException {
        Contact entity = contactService.exist(userId,contact.getContactUuid() );
        entity.setCustomName(contact.getContactName());
        entity = contactService.save(entity);
        return ContactDTO.fromEntity(modelMapper, entity);
    }

    public void deleteContact(String userId, String contactId){
        contactService.delete(userId, contactId);
    }

    public ContactDTO existContact(String userId, String contactId){
       return ContactDTO.fromEntity(modelMapper, contactService.exist(userId, contactId));
    }

    public Page<ContactDTO> findByName(String userId, String name){
        Page<Contact> contacts = contactService.findByName(userId, name, 0,20);
        return contacts.map(m -> ContactDTO.fromEntity(modelMapper, m));
    }

}
