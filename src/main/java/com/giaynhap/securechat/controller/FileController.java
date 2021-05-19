package com.giaynhap.securechat.controller;

import com.giaynhap.securechat.config.AppConfigure;
import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.model.response.ApiResponse;
import com.giaynhap.securechat.model.response.DTO.ContactDTO;
import com.giaynhap.securechat.model.response.DTO.ConversationDTO;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.service.serviceInterface.ImageService;
import com.giaynhap.securechat.sticker.StikerLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@RestController
public class FileController extends  BaseController {

    @Autowired
    ImageService imageService;

    @Autowired
    StikerLoader stikerLoader;

    public void genIfNotExistAvata(String uuid){
        String avatarPath = AppConfigure.shared.userAvatarPath ;
        File dir = new File(avatarPath);
        File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar."+uuid+".jpg");
        if (serverFile.exists()) {
            return;
        }

        try {
            UserInfoDTO userInfo = userManager.getUserInfo(uuid);
            BufferedImage img = imageService.avatarLetterImage(userInfo.getName());
            ImageIO.write(img,"jpg",serverFile);
        }catch (Exception e){

        }
    }

    @PostMapping("/users/avatar")
    public ResponseEntity<?> avatar(@RequestParam("file") MultipartFile file) throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userUuid = detail.getId();

        byte[] bytes = file.getBytes();
        String avatarPath = AppConfigure.shared.userAvatarPath ;
        File dir = new File(avatarPath);
        File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar."+userUuid+".jpg");
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        String avatarUrl = AppConfigure.shared.serverHost +"/avatar/"+userUuid;
        return ResponseEntity.ok(new ApiResponse<String>(0, AppConstant.SUCCESS_MESSAGE,avatarUrl));
    }

    @RequestMapping(value = "/users/avatar/{uuid}", method = RequestMethod.GET)
    public  @ResponseBody byte[] downloadAvatar(HttpServletResponse response, @PathVariable("uuid") String uuid, @RequestParam(name = "height",required = false) Integer width, @RequestParam(name = "height",required = false) Integer height) throws Exception {
        try {
            genIfNotExistAvata(uuid);
            String avatarPath = AppConfigure.shared.userAvatarPath;
            File dir = new File(avatarPath);
            File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar." + uuid + ".jpg");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (!serverFile.exists()) {
                serverFile = new File(dir.getAbsolutePath() + File.separator + "no_avatar.jpg");
            }
            BufferedImage img = ImageIO.read(serverFile);

            BufferedImage out;
            if (width != null && width > 10) {
                out = imageService.resize(img, width, height);
                out = imageService.cropAvatar(out);
                ImageIO.write(out, "jpg", stream);
            } else {
                img = imageService.cropAvatar(img);
                ImageIO.write(img, "jpg", stream);
            }
            String avatarUrl = AppConfigure.shared.serverHost + "/avatar/" + uuid;
            response.setHeader("Content-Disposition", "attachment; filename=\"avatar." + uuid + ".jpg\"");
            return stream.toByteArray();
        }catch (Exception e){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BufferedImage out = imageService.avatarLetterImage("Test");
            ImageIO.write(out, "jpg", stream);
            response.setHeader("Content-Disposition", "attachment; filename=\"avatar." + uuid + ".jpg\"");
            return stream.toByteArray();
        }
    }

    @RequestMapping(value = "/conversation/thumb/{uuid}/{useruuid}", method = RequestMethod.GET)
    public  @ResponseBody byte[] downloadConversationThumb(HttpServletResponse response, @PathVariable("uuid") String uuid,@PathVariable("useruuid") String useruuid, @RequestParam(name = "height",required = false) Integer width, @RequestParam(name = "height",required = false) Integer height) throws Exception {
        String avatarPath = AppConfigure.shared.userAvatarPath ;
        File dir = new File(avatarPath);
        String vicUUid = null;
        ConversationDTO conversation = conversationManager.getConversation(uuid) ;
        if (conversation == null ){
            throw new Exception("not found");
        }
        if (conversation.getUsers().size() == 2){
            Optional<UserInfoDTO> vic =  conversation.getUsers().stream().filter(userInfo -> !userInfo.getId().equals(useruuid)).findFirst();
            if (vic.isPresent()){
                vicUUid = vic.get().getId();
            }
        }

        if (vicUUid==null){
            vicUUid = "notfound";
        } else {
            genIfNotExistAvata(uuid);
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
        String avatarUrl = AppConfigure.shared.serverHost + "/avatar/" + vicUUid;
        response.setHeader("Content-Disposition", "attachment; filename=\"avatar." + vicUUid + ".jpg\"");
        return stream.toByteArray();
    }



    @PostMapping("file/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userUuid = detail.getId();

        String avatarPath = AppConfigure.shared.userAvatarPath + File.separator  +userUuid;
        File dir = new File(avatarPath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        String imageUuid = UUID.randomUUID().toString();

        File serverFile = new File(dir.getAbsolutePath() + File.separator + "image."+imageUuid+".jpg");
       /* BufferedImage img = ImageIO.read(file.getInputStream());

        ImageIO.write(img, "jpg", serverFile);

       */
        try (FileOutputStream outputStream = new FileOutputStream(serverFile)) {
            outputStream.write(file.getBytes());
            outputStream.close();
        }

        String avatarUrl =  "file/image/"+userUuid+"/"+imageUuid;
        return ResponseEntity.ok(new ApiResponse<String>(0, AppConstant.SUCCESS_MESSAGE,avatarUrl));
    }


    @PostMapping("file/audio")
    public ResponseEntity<?> uploadAudio(@RequestParam("file") MultipartFile file) throws Exception {
        UserInfoDTO detail = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userUuid = detail.getId();
        String avatarPath = AppConfigure.shared.userAvatarPath + File.separator  +userUuid;
        File dir = new File(avatarPath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        String imageUuid = UUID.randomUUID().toString();
        File serverFile = new File(dir.getAbsolutePath() + File.separator + "audio."+imageUuid+".aac");
        try (FileOutputStream outputStream = new FileOutputStream(serverFile)) {
            outputStream.write(file.getBytes());
            outputStream.close();
        }


        String audioUrl =  "file/audio/"+userUuid+"/"+imageUuid;
        return ResponseEntity.ok(new ApiResponse<String>(0, AppConstant.SUCCESS_MESSAGE,audioUrl));
    }

    @RequestMapping(value = "file/audio/{uuid}/{imguuid}", method = RequestMethod.GET)
    public  void downloadAudio(HttpServletResponse response,@PathVariable("imguuid") String imguuid, @PathVariable("uuid") String uuid) throws Exception {
        String avatarPath = AppConfigure.shared.userAvatarPath; ;

        File dir = new File(avatarPath);
        File serverFile = new File(dir.getAbsolutePath() + File.separator  +uuid+ File.separator  + "audio." + imguuid + ".aac");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        byte[] imgByte = Files.readAllBytes(serverFile.toPath());

        response.setHeader("Content-Type","audio/x-aac");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("audio/x-aac");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(imgByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }


    @RequestMapping(value = "file/image/{uuid}/{imguuid}", method = RequestMethod.GET)
    public  void downloadImage(HttpServletResponse response,@PathVariable("imguuid") String imguuid, @PathVariable("uuid") String uuid) throws Exception {
        String avatarPath = AppConfigure.shared.userAvatarPath;
        File dir = new File(avatarPath);
        File serverFile = new File(dir.getAbsolutePath() + File.separator  +uuid+ File.separator  + "image." + imguuid + ".jpg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (!serverFile.exists()) {
            serverFile = new File(dir.getAbsolutePath() + File.separator + "no_image.jpg");
        }



        byte[] imgByte = Files.readAllBytes(serverFile.toPath());

        response.setHeader("Content-Type","image/jpeg");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(imgByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @RequestMapping(value = "sticker/{sticker}/{index}", method = RequestMethod.GET)
    public void getStiker(HttpServletResponse response,@PathVariable("sticker") String model,@PathVariable("index") int index)  throws Exception{
        BufferedImage img = null;
        img = stikerLoader.getStiker(model).loadBitmapById(index);
        response.setHeader("Content-Type","image/jpeg");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        ImageIO.write(img,"png",responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @RequestMapping(value = "avatar_letter/{name}", method = RequestMethod.GET)
    public void getLetterAvatar(HttpServletResponse response,@PathVariable("name") String name)  throws Exception{
        BufferedImage img = imageService.avatarLetterImage(name);

        response.setHeader("Content-Type","image/jpeg");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        ImageIO.write(img,"png",responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
