package com.giaynhap.controller;

import com.giaynhap.config.AppConstant;
import com.giaynhap.handler.WebsocketCallHandler;
import com.giaynhap.model.*;
import com.giaynhap.model.DTO.ContactDTO;
import com.giaynhap.service.ContactServiceIml;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class ContactController {
    private static final Logger log = LoggerFactory.getLogger(WebsocketCallHandler.class);
    @Autowired
    ContactServiceIml contactService;
    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value = "/contact/page/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> contactList(@PathVariable("page") int page) throws Exception {
        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Contact> data = contactService.getPage(detail.getUsername(),page, 10);
        Page<ContactDTO> resData = data.map(contact -> ContactDTO.fromEntity(modelMapper,contact)) ;
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,resData));
    }

    @RequestMapping(value = "/contact/add", method = RequestMethod.POST)
    public ResponseEntity<?> addContact(@RequestBody  ContactDTO contact) throws Exception {
        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Contact entity = contact.toEntity(modelMapper);

        entity.setCreateAt(LocalDateTime.now());
        entity.setUserUuid(detail.getUsername());
        entity.setId((long) 0);
        entity = contactService.add(entity);
        if (entity == null){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }
        return ResponseEntity.ok(new ApiResponse<ContactDTO>(0,AppConstant.SUCCESS_MESSAGE,ContactDTO.fromEntity(modelMapper,entity)));
    }

    @RequestMapping(value = "/contact/update", method = RequestMethod.POST)
    public ResponseEntity<?> setContactName(@RequestBody  ContactDTO contact) throws Exception {
        Contact entity = contact.toEntity(modelMapper);

        entity = contactService.update(entity);
        if (entity == null){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }
        return ResponseEntity.ok(new ApiResponse<ContactDTO>(0,AppConstant.SUCCESS_MESSAGE,ContactDTO.fromEntity(modelMapper,entity)));
    }

    @RequestMapping(value = "/contact/delete/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getInfo(@PathVariable("uuid") String uuid ){
        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!contactService.delete(detail.getUsername(),uuid)){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }
        return ResponseEntity.ok(new ApiResponse<Object>(0,AppConstant.SUCCESS_MESSAGE,null));
    }

    @RequestMapping(value = "/contact/exist/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> existContact(@PathVariable("uuid") String uuid ){
        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Contact contact = contactService.exist(detail.getUsername(),uuid);
        if (contact == null){
            return ResponseEntity.ok(new ApiResponse<Object>(0,AppConstant.ERROR_MESSAGE,null));
        }
        return ResponseEntity.ok(new ApiResponse<Object>(0,AppConstant.SUCCESS_MESSAGE,contact));
    }


    @RequestMapping(value = "/contact/find/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable("name") String name) throws Exception {
        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Contact> data = contactService.findByName(name, 0,20);
        Page<ContactDTO> resData = data.map(contact -> ContactDTO.fromEntity(modelMapper,contact)) ;
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,resData));
    }



}
