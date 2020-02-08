package com.giaynhap.controller;

import com.giaynhap.config.AppConstant;
import com.giaynhap.handler.WebsocketCallHandler;
import com.giaynhap.model.ApiResponse;
import com.giaynhap.model.DTO.ContactDTO;
import com.giaynhap.model.DTO.UserInfoDTO;
import com.giaynhap.model.DTO.UserKeyDTO;
import com.giaynhap.model.UserInfo;
import com.giaynhap.model.UserKey;
import com.giaynhap.service.UserKeyServiceIml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class KeyProvideController {
    private static final Logger log = LoggerFactory.getLogger(WebsocketCallHandler.class);
    @Autowired
    UserKeyServiceIml userKeyService;
    @RequestMapping(value = "/keyprovider/public/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getPublicKey(@PathVariable("uuid") String uuid) throws Exception {

        String key = userKeyService.getKey(uuid).getPublicKey();
        if (key.trim().isEmpty()){
            throw new Exception("key blank");
        }
        return ResponseEntity.ok( new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE,key ) );
    }

    @RequestMapping(value = "/keyprovider/private", method = RequestMethod.GET)
    public ResponseEntity<?> getPrivateKey() throws Exception {
           UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String key = userKeyService.getKey(detail.getUsername()).getPrivateKey();
            if (key.trim().isEmpty()){
                throw new Exception("key blank");
            }
        return ResponseEntity.ok( new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE,key) );
    }

    @RequestMapping(value = "/keyprovider/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateKey(@RequestBody UserKeyDTO update) throws Exception
    {
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserKey userKey = userKeyService.getKey(detail.getUsername());
        if (userKey == null){
            userKey = new UserKey();
            userKey.setUUID( detail.getUsername());
        }
        userKey.setPrivateKey(update.getPrivateKey());
        if (update.getPublicKey() != null){
            userKey.setPublicKey(update.getPublicKey());
        }
        try {
            userKeyService.save(userKey);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return ResponseEntity.ok(new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE, ""));

    }


}
