package com.giaynhap.controller;

import com.giaynhap.config.AppConstant;
import com.giaynhap.model.*;
import com.giaynhap.model.DTO.ContactDTO;
import com.giaynhap.model.DTO.ConversationDTO;
import com.giaynhap.model.DTO.MessageDTO;
import com.giaynhap.model.DTO.UserKeyDTO;
import com.giaynhap.service.*;

import com.giaynhap.sticker.StikerInfo;
import com.giaynhap.sticker.StikerLoader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ConversationController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private ConversationServiceIml conversationService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserConversationServiceIml userConversationService;

    @RequestMapping(value = "/conversation/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getConversation(@PathVariable("uuid") String uuid){
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conversation conversation = conversationService.get(uuid);
        if (conversation.getName() == null)
        conversation.setName(ConversationDTO.createName(conversation.getUsers(),detail.getUsername()));
        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE, ConversationDTO.fromEntity(modelMapper,conversation)));
    }

    @RequestMapping(value = "/conversation/u/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserConversation(@PathVariable("uuid") String uuid){
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (uuid.equals(detail.getUsername())){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }
        Conversation conversation = conversationService.getUserConversation(detail.getUsername(),uuid);
        if (conversation == null){

            UserInfo userInfo = userService.getUserInfo(uuid);
            if ( userInfo == null ){
                return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
            }

            conversation = new Conversation();

            conversation.setName(null);
            conversation.setUser_uuid(detail.getUsername());
            conversation.setCreateAt(LocalDateTime.now());
            conversation.setLastMessageAt(LocalDateTime.now());
            conversation.setUUID(UUID.randomUUID().toString());
            conversation = conversationService.add(conversation);


            if (conversation != null) {
                conversationService.addUser(conversation.getUUID(), userInfo.getUUID());
            }
            if (conversation.getName() == null)
            conversation.setName(ConversationDTO.createName(conversation.getUsers(),detail.getUsername()));
        }

        if (conversation == null){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }

        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE, ConversationDTO.fromEntity(modelMapper,conversation)));
    }

    @RequestMapping(value = "/conversation/new", method = RequestMethod.POST)
    public ResponseEntity<?> getConversation(@RequestBody ConversationDTO con) throws Exception{
        Conversation conversation = con.toEntity(modelMapper);
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        conversation.setUser_uuid(detail.getUsername());
        conversation.setCreateAt(LocalDateTime.now());
        conversation.setLastMessageAt(LocalDateTime.now());
        conversation.setUUID(UUID.randomUUID().toString());
        conversation = conversationService.add(conversation);
        conversation.setName(ConversationDTO.createName(conversation.getUsers(),detail.getUsername()));


        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE, ConversationDTO.fromEntity(modelMapper,conversation)));
    }
    @RequestMapping(value = "/conversation/addUser/{conversation}", method = RequestMethod.POST)
    public  ResponseEntity<?> getConversation(@RequestBody List<UserConversation> users, @PathVariable("conversation") String conversationUUID) throws Exception{
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        for (UserConversation u : users){
            conversationService.addUser(conversationUUID, u.getUserUuid(),u.getKey());
        }
//        conversation.setUsers();


        return ResponseEntity.ok(new ApiResponse<Object>(0, AppConstant.SUCCESS_MESSAGE,  null));
    }

    @RequestMapping(value = "/conversation/new/{uuid}", method = RequestMethod.POST)
    public ResponseEntity<?> getConversation(@PathVariable("uuid") String uuid,@RequestBody ConversationDTO con) throws Exception{
        Conversation conversation = con.toEntity(modelMapper);
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo userInfo = userService.getUserInfo(uuid);
        if ( userInfo == null ){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }

        // kiem tra conversation da ton tai
        conversation = conversationService.getUserConversation(detail.getUsername(),uuid);
        if (conversation == null) {
            conversation.setName(null);
            conversation.setUser_uuid(detail.getUsername());
            conversation.setCreateAt(LocalDateTime.now());
            conversation.setLastMessageAt(LocalDateTime.now());
            conversation.setUUID(UUID.randomUUID().toString());
            conversation = conversationService.add(conversation);

            if (conversation != null) {
                conversationService.addUser(conversation.getUUID(), userInfo.getUUID());
            } else {
                return ResponseEntity.ok(new ApiResponse<Object>(1, AppConstant.ERROR_MESSAGE, null));
            }
            if (conversation.getName() == null)
            conversation.setName(ConversationDTO.createName(conversation.getUsers(),detail.getUsername()));
        }

        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE, ConversationDTO.fromEntity(modelMapper,conversation)));
    }

    @RequestMapping(value = "/conversation/page/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> conversationList(@PathVariable("page") int page) throws Exception {

        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Conversation> data = conversationService.getPage(detail.getUsername(),page, 10);
        Page<ConversationDTO> resData = data.map(conversation -> {
            ConversationDTO dto = ConversationDTO.fromEntity(modelMapper,conversation);
            if (conversation.getName() == null)
            dto.setName(ConversationDTO.createName(conversation.getUsers(),detail.getUsername()));
            return  dto;
        }) ;

        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,resData));
    }

    @RequestMapping(value = "/conversation/messages/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> conversationMessage(@PathVariable("uuid") String uuid,@RequestParam("time") long time) throws Exception {

        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime localDateTime  = null;
        if (time > 0) {
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        }
        List<com.giaynhap.model.Message> data = conversationService.getMessage(uuid,detail.getUsername(), localDateTime);
        List<MessageDTO> resData = data.stream().map(message -> MessageDTO.fromEntity(modelMapper,message)).collect(Collectors.toList()) ;

        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,resData));
    }

    @RequestMapping(value = "/conversation/udpatekey/{uuid}", method = RequestMethod.POST)
    public ResponseEntity<?> updateKey(@PathVariable("uuid") String uuid,@RequestBody() List<UserConversation> users) throws Exception {
        UserDetails detail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conversation conversation = conversationService.get(uuid);
        if (!conversation.getUser_uuid().equals(detail.getUsername())){
            return  ResponseEntity.ok(new ApiResponse(1,AppConstant.SUCCESS_MESSAGE,null));
        }
        for (UserConversation uc : users)
        {
            userConversationService.updateKey(uuid,uc.getUserUuid(), uc.getKey());
        }
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,null));
    }
    @RequestMapping(value = "/sticker/list", method = RequestMethod.GET)
    public ResponseEntity<?> getListStiker( ) throws Exception {

        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE, StikerLoader.getInstance().getStickers() ));
    }

}
