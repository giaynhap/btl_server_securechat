package com.giaynhap.securechat.manager;

import com.giaynhap.securechat.exception.ApiException;
import com.giaynhap.securechat.model.UserKey;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.model.response.DTO.UserKeyDTO;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserKeyManager extends BaseManager{

    public String getPublicKey(String uuid) throws ApiException {
        String key = userKeyService.getKey(uuid).getPublicKey();
        if (key.trim().isEmpty()){
            throw new ApiException(1,"Key empty");
        }
        return key;
    }

    public String getPrivateKey(String uuid) throws  ApiException {
        String key = userKeyService.getKey(uuid).getPrivateKey();
        if (key.trim().isEmpty()){
            throw new ApiException(1,"Key empty");
        }
        return key;
    }

    public boolean updateKey(String uuid , UserKeyDTO updateUserKey){

        UserKey userKey = userKeyService.getKey( uuid );
        if (userKey == null){
            userKey = new UserKey();
            userKey.setUUID( new ObjectId(uuid));
        }

        userKey.setPrivateKey(updateUserKey.getPrivateKey());
        if (updateUserKey.getPublicKey() != null){
            userKey.setPublicKey(updateUserKey.getPublicKey());
        }
        try {
            userKeyService.save(userKey);
        } catch (Exception e){
            return  false;
        }
        return true;
    }


}
