package com.giaynhap.controller;

import com.giaynhap.config.AppConfigure;
import com.giaynhap.config.AppConstant;
import com.giaynhap.model.ApiResponse;
import com.giaynhap.model.Conversation;
import com.giaynhap.model.UserInfo;
import com.giaynhap.service.ConversationServiceIml;
import com.giaynhap.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

@RestController
public class FileController {

    @Autowired
    ImageService imageService;
    @Autowired
    ConversationServiceIml conversationServiceIml;

    @PostMapping("/users/avatar")
    public ResponseEntity<?> avatar(@RequestParam("file") MultipartFile file, @RequestParam("ext") String ext) throws Exception {
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userUuid = detail.getUsername();

        byte[] bytes = file.getBytes();
        String avatarPath = AppConfigure.getConfig("user.avatar.path") ;
        File dir = new File(avatarPath);
        File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar."+userUuid+".jpg");
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        String avatarUrl = AppConfigure.getConfig("server.host") +"/avatar/"+userUuid;
        return ResponseEntity.ok(new ApiResponse<String>(0, AppConstant.SUCCESS_MESSAGE,avatarUrl));
    }

    @RequestMapping(value = "/users/avatar/{uuid}", method = RequestMethod.GET)
    public  @ResponseBody byte[] downloadAvatar(HttpServletResponse response, @PathVariable("uuid") String uuid, @RequestParam(name = "height",required = false) Integer width, @RequestParam(name = "height",required = false) Integer height) throws Exception {
        String avatarPath = AppConfigure.getConfig("user.avatar.path") ;
        File dir = new File(avatarPath);
        File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar." + uuid + ".jpg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (!serverFile.exists()) {
            serverFile = new File(dir.getAbsolutePath() + File.separator + "no_avatar.jpg");
        }
        BufferedImage img = ImageIO.read(serverFile);
        BufferedImage out ;
        if (width != null && width > 10 ) {
            out = imageService.resize(img, width, height);
            ImageIO.write(out, "jpg", stream);
        }else {
            ImageIO.write(img, "jpg", stream);
        }
        String avatarUrl = AppConfigure.getConfig("server.host") + "/avatar/" + uuid;
        response.setHeader("Content-Disposition", "attachment; filename=\"avatar." + uuid + ".jpg\"");
        return stream.toByteArray();
    }

    @RequestMapping(value = "/conversation/thumb/{uuid}/{useruuid}", method = RequestMethod.GET)
    public  @ResponseBody byte[] downloadConversationThumb(HttpServletResponse response, @PathVariable("uuid") String uuid,@PathVariable("useruuid") String useruuid, @RequestParam(name = "height",required = false) Integer width, @RequestParam(name = "height",required = false) Integer height) throws Exception {
        String avatarPath = AppConfigure.getConfig("user.avatar.path") ;
        File dir = new File(avatarPath);
        String vicUUid = null;
       Conversation conversation =  conversationServiceIml.get(uuid);
       if (conversation == null ){
           throw new Exception("not found");
       }
       if (conversation.getUsers().size() == 2){
           Optional<UserInfo> vic =  conversation.getUsers().stream().filter(userInfo -> !userInfo.getUUID().equals(useruuid)).findFirst();
            if (vic.isPresent()){
                vicUUid = vic.get().getUUID();
            }
       }

       if (vicUUid==null){
           vicUUid = "notfound";
       }

        File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar." + vicUUid + ".jpg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (!serverFile.exists()) {
            serverFile = new File(dir.getAbsolutePath() + File.separator + "no_avatar.jpg");
        }
        BufferedImage img = ImageIO.read(serverFile);
        BufferedImage out ;
        if (width != null && width > 10 ) {
            out = imageService.resize(img, width, height);
            ImageIO.write(out, "jpg", stream);
        }else {
            ImageIO.write(img, "jpg", stream);
        }
        String avatarUrl = AppConfigure.getConfig("server.host") + "/avatar/" + vicUUid;
        response.setHeader("Content-Disposition", "attachment; filename=\"avatar." + vicUUid + ".jpg\"");
        return stream.toByteArray();
    }
}
