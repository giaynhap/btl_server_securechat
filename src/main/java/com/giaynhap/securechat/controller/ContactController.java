package com.giaynhap.securechat.controller;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.response.ApiResponse;
import com.giaynhap.securechat.model.response.DTO.ContactDTO;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactController extends BaseController {
    @RequestMapping(value = "/contact/page/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> contactList(@PathVariable("page") int page) throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<ContactDTO> data = contactManager.getPageContact(detail.getId(), page, 10);
        return  ResponseEntity.ok(new ApiResponse(0, AppConstant.SUCCESS_MESSAGE,data));
    }

    @RequestMapping(value = "/contact/add", method = RequestMethod.POST)
    public ResponseEntity<?> addContact(@RequestBody ContactDTO contact) throws Exception {
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContactDTO result = contactManager.addContact(detail.getId(),contact);
        return ResponseEntity.ok(new ApiResponse<ContactDTO>(0,AppConstant.SUCCESS_MESSAGE, result ));
    }

    @RequestMapping(value = "/contact/update", method = RequestMethod.POST)
    public ResponseEntity<?> setContactName(@RequestBody  ContactDTO contact) throws Exception {
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContactDTO entity = contactManager.updateContactName(detail.getId(), contact);
        if (entity == null){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }
        return ResponseEntity.ok(new ApiResponse<ContactDTO>(0,AppConstant.SUCCESS_MESSAGE, entity));
    }

    @RequestMapping(value = "/contact/delete/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getInfo(@PathVariable("uuid") String uuid ){
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contactManager.deleteContact(detail.getId(), uuid);
        return ResponseEntity.ok(new ApiResponse<Object>(0,AppConstant.SUCCESS_MESSAGE,null));
    }

    @RequestMapping(value = "/contact/exist/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> existContact(@PathVariable("uuid") String uuid ){
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContactDTO contact = contactManager.existContact(detail.getId(), uuid);
        if (contact == null){
            return ResponseEntity.ok(new ApiResponse<Object>(0,AppConstant.ERROR_MESSAGE,null));
        }
        return ResponseEntity.ok(new ApiResponse<Object>(0,AppConstant.SUCCESS_MESSAGE,contact));
    }

    @RequestMapping(value = "/contact/find/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable("name") String name) throws Exception {
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<ContactDTO> data = contactManager.findByName(detail.getId(), name);
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,data));
    }

    @RequestMapping(value = "/contact/online", method = RequestMethod.GET)
    public ResponseEntity<?> getOnlineUsers() throws Exception {
        try {
            List<ContactDTO> dtos = new ArrayList<ContactDTO>();
            return ResponseEntity.ok(new ApiResponse<>(0,AppConstant.SUCCESS_MESSAGE, dtos));
        }catch ( Exception e){
            return ResponseEntity.ok(new ApiResponse<>(0,e.toString(), null));
        }
    }
}
