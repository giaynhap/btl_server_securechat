package com.giaynhap.securechat.controller;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.UserConversation;
import com.giaynhap.securechat.model.response.ApiResponse;
import com.giaynhap.securechat.model.response.DTO.ConversationDTO;
import com.giaynhap.securechat.model.response.DTO.MessageDTO;
import com.giaynhap.securechat.model.response.DTO.UserConversationDTO;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.sticker.StikerInfo;
import com.giaynhap.securechat.sticker.StikerLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConversationController extends BaseController{
    @Autowired
    StikerLoader stikerLoader;

    @RequestMapping(value = "/conversation/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getConversation(@PathVariable("uuid") String uuid){
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ConversationDTO conversation = conversationManager.getConversation(uuid);
        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE,  conversation ));
    }

    @RequestMapping(value = "/conversation/page/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> conversationList(@PathVariable("page") int page) throws Exception {

        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<ConversationDTO> data = conversationManager.getPage(detail.getId(),page, 10);

        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,data));
    }

    @RequestMapping(value = "/sticker/list", method = RequestMethod.GET)
    public ResponseEntity<?> getListStiker( ) throws Exception {
        ArrayList<StikerInfo> stickers = stikerLoader.getStickers();
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE, stickers));
    }


    @RequestMapping(value = "/conversation/new", method = RequestMethod.POST)
    public ResponseEntity<?> getConversation(@RequestBody ConversationDTO con) throws Exception{

        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ConversationDTO conversation = conversationManager.createConversation(con.getUserUuid(), con.getUsers().stream().map(m -> m.getId()).collect(Collectors.toList()));
        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE,  conversation ));
    }

    @RequestMapping(value = "/conversation/u/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserConversation(@PathVariable("uuid") String uuid){
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (uuid.equals(detail.getId())){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }

        ConversationDTO conversation = conversationManager.getConversation(detail.getId(),uuid);
        if (conversation == null){
            ArrayList<String> xs = new ArrayList<>();
            xs.add(uuid);
            conversation = conversationManager.createConversation(detail.getId(), xs);
        }

        if (conversation == null){
            return ResponseEntity.ok(new ApiResponse<Object>(1,AppConstant.ERROR_MESSAGE,null));
        }

        conversation.setName(ConversationDTO.createName(conversation.getUsers(), detail.getId()));

        return ResponseEntity.ok(new ApiResponse<ConversationDTO>(0, AppConstant.SUCCESS_MESSAGE, conversation ));
    }

    @RequestMapping(value = "/conversation/addUser/{conversation}", method = RequestMethod.POST)
    public  ResponseEntity<?> getConversation(@RequestBody List<UserConversation> users, @PathVariable("conversation") String conversationUUID) throws Exception{
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

/*
        for (UserConversation u : users){
            conversationManager.addUser(conversationUUID, u.getUserUuid(),u.getKey());
        }*/
//        conversation.setUsers();


        return ResponseEntity.ok(new ApiResponse<Object>(0, AppConstant.SUCCESS_MESSAGE,  null));
    }

    @RequestMapping(value = "/conversation/messages/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> conversationMessage(@PathVariable("uuid") String uuid,@RequestParam("time") long time) throws Exception {

        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime localDateTime  = null;
        if (time > 0) {
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        }
        List<MessageDTO> resData = conversationManager.getConversationMessages(uuid, localDateTime, 20) ;

        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,resData));
    }


    @RequestMapping(value = "/conversation/{uuid}/send", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@PathVariable("uuid") String uuid,@RequestBody MessageDTO dto) throws Exception {
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        dto.setSenderUuid(detail.getId());
        dto.setSender(detail);

        conversationManager.addMessage(dto);
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,null));
    }


    @RequestMapping(value = "/conversation/udpatekey/{uuid}", method = RequestMethod.POST)
    public ResponseEntity<?> updateKey(@PathVariable("uuid") String uuid,@RequestBody() List<UserConversationDTO> users) throws Exception {
        UserInfoDTO detail = (UserInfoDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ConversationDTO conversation = conversationManager.getConversation(uuid);
        if (!conversation.getUserUuid().equals(detail.getId())){
            return  ResponseEntity.ok(new ApiResponse(1,AppConstant.ERROR_MESSAGE,null));
        }

        for (UserConversationDTO uc : users)
        {
            if (!conversationManager.updateKey(uuid,uc.getUserUuid(), uc.getKey())){
                return  ResponseEntity.ok(new ApiResponse(2,AppConstant.ERROR_MESSAGE,null));
            }
        }
        return  ResponseEntity.ok(new ApiResponse(0,AppConstant.SUCCESS_MESSAGE,null));
    }

}
