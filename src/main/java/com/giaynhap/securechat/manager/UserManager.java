package com.giaynhap.securechat.manager;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.giaynhap.securechat.config.AppConfigure;
import com.giaynhap.securechat.exception.ApiException;
import com.giaynhap.securechat.exception.ApiOptException;
import com.giaynhap.securechat.model.Device;
import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.model.UserKey;
import com.giaynhap.securechat.model.request.AuthenRequest;
import com.giaynhap.securechat.model.request.UserRegistRequest;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.service.serviceInterface.DeviceService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserManager  extends BaseManager {

    boolean enableOTP = true;

    public UserInfoDTO quickChangeName(String uuid, String newName) throws ApiException {
        UserInfo info = userService.getUserInfo(uuid);
        info.setName(newName);
        return UserInfoDTO.fromEntity(modelMapper, userService.save(info));
    }

    public UserInfoDTO preLogin(String username, String deviceCode) throws ApiException {
        User user = userService.getByUsername(username);
        if (enableOTP) {
            Device device = deviceService.getDeviceByDeviceCode(user.getId().toHexString(), deviceCode);
            if (device == null) {
                throw new ApiOptException(ApiOptException.OptExceptionCode.SEND, genOtpTransaction(user));
            }
        }
        UserInfoDTO info = UserInfoDTO.fromEntity(modelMapper, user.getUserInfo());
        return info;
    }

    public UserInfoDTO getUserInfoByUsername(String username){
        User user = userService.getByUsername(username);
        UserInfoDTO info = UserInfoDTO.fromEntity(modelMapper, user.getUserInfo());
        return info;
    }


    public UserInfoDTO getByUserName(String userName) throws ApiException {
       User user =  userService.getByUsername(userName);
       if (user == null) {
             throw new ApiException(1,"Not found");
       }

       return UserInfoDTO.fromEntity(modelMapper, user.getUserInfo());
    }

    public UserInfoDTO getByID(String uid) throws ApiException {
        UserInfo user =  userService.getUserInfo(uid);
        if (user == null) {
            throw new ApiException(1,"Not found");
        }

        return UserInfoDTO.fromEntity(modelMapper, user);
    }

    public List<UserInfoDTO> findByName(String name){
        List<UserInfo> users = userService.findByName(name);
        return users.stream().map( m -> UserInfoDTO.fromEntity(modelMapper, m) ).collect(Collectors.toList());
    }

    public UserInfoDTO register(UserRegistRequest regist) throws ApiException {
        User user = userService.getByUsername(regist.getUsername());
        if (user != null){
            throw new ApiException(1,"Username exist");
        }
        ObjectId mongodId = new ObjectId();
        String id = mongodId.toHexString();
        User login = new User();
        login.setAccount( regist.getUsername() );
        login.setId(mongodId);
        String hashString =  bHasher.hashToString(12,regist.getPassword().toCharArray());
       // String token = otpService.genOTPCode(hashString);
        login.setPassword( hashString );
        login.setCreate_at(LocalDateTime.now());
        login.setEnable(true);
       // login.setToken(token);
        login.setTokenTime(LocalDateTime.now());


        UserInfo userInfo = new UserInfo();
        userInfo.setName(regist.getName());
        userInfo.setAddress(regist.getAddress());
        userInfo.setDob(regist.getDob());
        userInfo.setPhone(regist.getPhonenumber());
        userInfo.setId(mongodId);


        UserKey userKey = new UserKey();
        userKey.setPublicKey("");
        userKey.setPrivateKey("");
        userKey.setUUID(mongodId);

        userService.save(login,userInfo,userKey);
        try {
            BufferedImage img = imageService.avatarLetterImage(userInfo.getName());
            String avatarPath =  AppConfigure.shared.userAvatarPath;
            File dir = new File(avatarPath);
            File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar." + id + ".jpg");
            ImageIO.write(img,"jpg",serverFile);
        }catch (Exception e){

        }

        return UserInfoDTO.fromEntity(modelMapper, userInfo);

    }

    public UserInfoDTO authenticate(AuthenRequest authenRequest) throws ApiException {
        Device device = authenRequest.getDevice();
        if ( false && device == null )
        {
           throw new ApiException(-1,"Bad request");
        }

        User user  = userService.getByUsername(authenRequest.getUsername());

        if (user == null){
            throw new ApiException(1,"Username not exist");
        }

        BCrypt.Result  result = BCrypt.verifyer().verify(authenRequest.getPassword().toCharArray(), user.getPassword());

        if (result == null || !result.verified){
            throw new ApiException(2,"Incorrect password");
        }
        if (enableOTP) {
            Device savedDevice = deviceService.getDeviceByDeviceCode(user.getId().toHexString(),device.getDeviceCode());
            Boolean needCheckToken = false;
            String otp = authenRequest.getToken();

                if (savedDevice == null && (
                        (authenRequest.getToken() == null || authenRequest.getToken().isEmpty()) ||
                                (authenRequest.getTransactionId() == null || authenRequest.getTransactionId().isEmpty())
                )
                ) {
                    throw new ApiOptException(ApiOptException.OptExceptionCode.SEND, genOtpTransaction(user));
                } else if (savedDevice == null)  {
                    needCheckToken = true;
                } else {
                    needCheckToken = false;
                }
            

            if (needCheckToken) {
                String token = "";

                long transactionId = Long.parseLong(authenRequest.getTransactionId());
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime from = LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionId), ZoneId.systemDefault());
                Duration duration = Duration.between(from, now);

                if (duration.getSeconds() > 30) {
                    throw new ApiOptException(ApiOptException.OptExceptionCode.EXPIRED);
                }

                try {
                    token = otpService.genOTPCode(user.getPassword(), transactionId);
                } catch (Exception e) {
                    throw new ApiOptException(ApiOptException.OptExceptionCode.UNKNOWN);
                }
                
                if (token.isEmpty() || !token.equals(otp)) {
                    throw new ApiOptException(ApiOptException.OptExceptionCode.INCORRECT);
                }


                device.setUserUuid(user.getId());
                if (device.getUUID() == null){
                    device.setUUID(new ObjectId());
                }
                deviceService.saveDevice(device);
            }
        }

        return UserInfoDTO.fromEntity(modelMapper, user.getUserInfo());
    }

    Long genOtpTransaction(User user) throws ApiException {
        Long time = new Date().getTime();
        String token = "";

        try {
            token = otpService.genOTPCode(user.getPassword(),time);
        } catch (Exception e) {
            throw new ApiOptException(ApiOptException.OptExceptionCode.UNKNOWN );
        }
      //  otpService.sendOTP(user.getUserInfo().getPhone(), token);
        return time;
    }

    public UserInfoDTO getUserInfo(String uuid){
       UserInfo userInfo =  userService.getUserInfo(uuid);
       return UserInfoDTO.fromEntity(modelMapper, userInfo);
    }


}
