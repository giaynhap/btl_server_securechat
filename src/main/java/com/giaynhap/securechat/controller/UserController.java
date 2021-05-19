package com.giaynhap.securechat.controller;
import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.exception.ApiException;
import com.giaynhap.securechat.exception.ApiOptException;
import com.giaynhap.securechat.model.Message;
import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.request.AuthenRequest;
import com.giaynhap.securechat.model.request.UserRegistRequest;
import com.giaynhap.securechat.model.response.ApiResponse;
import com.giaynhap.securechat.model.response.AuthenResponse;
import com.giaynhap.securechat.model.response.DTO.ConversationDTO;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
//0339092543
@CrossOrigin
@RestController
public class UserController extends  BaseController {

    @Autowired
    SocketService socketService;
    @RequestMapping(value = "/makepass/{password}", method = RequestMethod.GET)
    public String  testEncryptPassword(@PathVariable("password") String password) throws Exception {
        String hashString =  bHasher.hashToString(12,password.toCharArray());
        return hashString ;
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object getAllUsers() {
        List<User> list =  new ArrayList<User>();

      //  return conversationManager.getConversation("609058b07918df6082035c9c",null);
       // List<String >x = new ArrayList<>();
        //x.add("609013b684682af8f7ade608");
       //  conversationManager.createConversation("609058b07918df6082035c9c",x);
        //return conversationManager.getConversation("609058b07918df6082035c9c",x.get(0));
        SocketService.MessageFilter messageFilter = new SocketService.MessageFilter();
        messageFilter.setMessage("test");
        messageFilter.setMessageId("123");
        socketService.sendMessageQueue(messageFilter);
        return "done";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenRequest request) {
        try {
            UserInfoDTO user = userManager.authenticate(request);
            final String token = jwtTokenUtil.generateToken(user.getId());
            final String refreshToken = jwtTokenUtil.generateRefreshToken(token);
            AuthenResponse authenResponse = new AuthenResponse(token,refreshToken);
            authenResponse.setUser(user);
            userService.cacheUser(user);
            return ResponseEntity.ok(new ApiResponse<AuthenResponse>(0, AppConstant.SUCCESS_MESSAGE, authenResponse));
        } catch (ApiOptException e){
            if (e.getError() != ApiOptException.OptExceptionCode.SEND){
                return ResponseEntity.ok(new ApiResponse<String>(e.getCode(), e.getMessage(), null));
            }
            return ResponseEntity.ok(new ApiResponse<String>(e.getCode(), e.getMessage(),null, e.getData().toString()));
        }
        catch (ApiException e) {
            LOG.info("Request Error : "+e.getMessage());
            return ResponseEntity.ok(new ApiResponse<String>(e.getCode(), e.getMessage(), null));
        }

    }

    @RequestMapping(value = "/users/info/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@PathVariable("uuid") String uuid) throws Exception {
        UserInfoDTO info = userManager.getUserInfo(uuid);
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, info));
    }



    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserRegistRequest regist) throws Exception {
        if (!validateUsername(regist.getUsername())){
            throw new Exception("Username validate error");
        }

        if (!validatePassword(regist.getPassword())){
            throw new Exception("Password validate error");
        }

        try{
            UserInfoDTO info = userManager.register(regist);
            return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, info ));
        } catch (ApiException e){
            return ResponseEntity.ok(new ApiResponse<String>(e.getCode(), e.getMessage(), null));
        }
    }

    @RequestMapping(value = "/users/find/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable("name") String name) throws Exception {
        List<UserInfoDTO> infos = userManager.findByName(name);
        return ResponseEntity.ok(new ApiResponse<List<UserInfoDTO>>(0,AppConstant.SUCCESS_MESSAGE, infos));
    }

    @RequestMapping(value = "/users/exist/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUserName(@PathVariable("username") String username) throws Exception {
        UserInfoDTO info = userManager.getByUserName(username);
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE,  info  ));
    }

    @RequestMapping(value = "/users/prelogin/{device}/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUserName(@PathVariable("device") String deviceCode,@PathVariable("username") String username) throws Exception {
        UserInfoDTO userInfo = null;
        try {
            userInfo = userManager.preLogin(username, deviceCode);
        }catch (ApiException e){
            userInfo = userManager.getByUserName(username);
            return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(1,AppConstant.ERROR_MESSAGE, userInfo ));
        }
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE,  userInfo ));
    }

    @RequestMapping(value = "/users/quick/name", method = RequestMethod.POST)
    public ResponseEntity<?> changeUserInfoName(@RequestBody String username) throws Exception {
        username = username.trim().replace("\"","");
        if (username.isEmpty()){
            throw new Exception("NULL");
        }
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        detail = userManager.quickChangeName(detail.getId(), username);
        userService.cacheUser(detail);
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE,  detail  ));
    }

    @RequestMapping(value = "/users/suggest", method = RequestMethod.GET)
    public ResponseEntity<?> getSuggest() throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       // List<UserInfo> users = userService.getRandomSuggest(detail.getUsername());
        List<UserInfoDTO> dtos = new ArrayList<>(); //users.stream().map(m->UserInfoDTO.fromEntity(modelMapper,m)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(0,AppConstant.SUCCESS_MESSAGE, dtos));
    }

    @RequestMapping(value = "/users/info", method = RequestMethod.GET)
    public ResponseEntity<?> getUser() throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoDTO info = userManager.getUserInfo(detail.getId());
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, info));
    }


}

