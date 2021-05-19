package com.giaynhap.securechat.controller;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.exception.ApiException;
import com.giaynhap.securechat.model.response.ApiResponse;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.model.response.DTO.UserKeyDTO;
import com.giaynhap.securechat.service.serviceInterface.UserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class KeyProvideController extends BaseController  {

    @Autowired
    UserKeyService userKeyService;

    @RequestMapping(value = "/keyprovider/public/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getPublicKey(@PathVariable("uuid") String uuid) throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            String key = userKeyManager.getPublicKey(detail.getId());
            return ResponseEntity.ok( new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE,key) );
        } catch (ApiException e){
            return ResponseEntity.ok( new ApiResponse<String>(e.getCode(),e.getMessage(),null) );
        }
    }

    @RequestMapping(value = "/keyprovider/private", method = RequestMethod.GET)
    public ResponseEntity<?> getPrivateKey() throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            String key = userKeyManager.getPrivateKey(detail.getId());
            return ResponseEntity.ok( new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE,key) );
        } catch (ApiException e){
            return ResponseEntity.ok( new ApiResponse<String>(e.getCode(),e.getMessage(),null) );
        }

    }

    @RequestMapping(value = "/keyprovider/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateKey(@RequestBody UserKeyDTO update) throws Exception
    {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if (!userKeyManager.updateKey(detail.getId(),update)){
           return ResponseEntity.ok(new ApiResponse<String>(1,AppConstant.ERROR_MESSAGE, ""));
       }
        return ResponseEntity.ok(new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE, ""));

    }


}
