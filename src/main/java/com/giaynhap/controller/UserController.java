package com.giaynhap.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.giaynhap.config.AppConfigure;
import com.giaynhap.config.AppConstant;
import com.giaynhap.config.JwtTokenUtil;
import com.giaynhap.model.*;
import com.giaynhap.model.DTO.UserInfoDTO;
import com.giaynhap.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController

public class UserController {
    @RequestMapping({ "/users" })
    public String test(){
        return "test";
    }

    @Autowired
    UserOnlineController onlineController;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserServiceIml userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private DeviceServiceIml deviceService;
    @Autowired
    private OtpService otpService;
    @Autowired
    UserKeyServiceIml userKeyService;
    @Autowired
    ImageService imageService;

    @Autowired
    BCrypt.Hasher bHasher;

    @RequestMapping(value = "/makepass/{password}", method = RequestMethod.GET)
    public String  testEncryptPassword(@PathVariable("password") String password) throws Exception {
        String hashString =  bHasher.hashToString(12,password.toCharArray());
        return hashString ;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Device device = authenticationRequest.getDevice();
        if ( device == null )
        {
            return ResponseEntity.ok(new ApiResponse<JwtResponse>(1, AppConstant.ERROR_MESSAGE, null));
        }


        final Users user = authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());

        if (deviceService.getDeviceByDeviceCode(user.getUUID(),device.getDeviceCode()) == null ){
                String otp = user.getToken();
                if ( otp != null && !otp.trim().isEmpty())
                {
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between( user.getTokenTime(),now);
                   
                    String token = otpService.genOTPCode(user.getPassword());

                    if (!otp.equals(authenticationRequest.getToken()))
                    {
                        return ResponseEntity.ok(new ApiResponse<JwtResponse>(2, AppConstant.ERROR_MESSAGE, null));
                    }

                    if (duration.getSeconds() > 30){

                        user.setToken(token);
                        user.setTokenTime( LocalDateTime.now() );
                        userService.save(user);
                        otpService.sendOtp(user.getUserInfo().getPhone(),token);
                        return ResponseEntity.ok(new ApiResponse<JwtResponse>(4, AppConstant.ERROR_MESSAGE, null));
                    }
                    device.setDeviceOs("android");
                    device.setUUID(UUID.randomUUID().toString());
                    device.setUserUuid(user.getUUID());
                    deviceService.addDevice(device);
                    user.setToken(null);
                    user.setEnable(true);
                    userService.save(user);

                }else{
                    String token = otpService.genOTPCode(user.getPassword());
                    user.setToken(token);
                    user.setTokenTime( LocalDateTime.now() );
                    userService.save(user);
                    otpService.sendOtp(user.getUserInfo().getPhone(),token);

                    return ResponseEntity.ok(new ApiResponse<JwtResponse>(3, AppConstant.ERROR_MESSAGE, null));
                }
        }



        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUUID());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(token);


        return ResponseEntity.ok(new ApiResponse<JwtResponse>(0, AppConstant.SUCCESS_MESSAGE,new JwtResponse(token,refreshToken)));
    }
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@RequestBody JwtRefreshToken refreshTokenRequest) throws Exception {
        if (jwtTokenUtil.isTokenExpired(refreshTokenRequest.getToken())){
            throw  new Exception("Token Expired");
        }
        String serverRefreshToken = jwtTokenUtil.generateRefreshToken(refreshTokenRequest.getToken());
        if (!serverRefreshToken.equals(refreshTokenRequest.getRefreshToken())){
            throw  new Exception("Refresh token failed.");
        }
        final String token = jwtTokenUtil.refreshToken(refreshTokenRequest.getToken());
        final String refreshToken = jwtTokenUtil.generateRefreshToken(token);

        return ResponseEntity.ok(new ApiResponse<JwtResponse>(0, AppConstant.SUCCESS_MESSAGE,new JwtResponse(token,refreshToken)));
    }

    private Users authenticate(String username, String password) throws Exception {
        UserInfo info = userService.findByUserName(username);
        if (info == null){
            throw  new Exception("User not found");
        }
        Users  user =   userService.getUser(info.getUUID());


        if (user  == null){
            throw  new Exception("User not found");
        }

        BCrypt.Result  resul = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (resul == null || !resul.verified){
            throw  new Exception("Password Incorrect");
        }
        return user;
    }



    @RequestMapping(value = "/users/info/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@PathVariable("uuid") String uuid) throws Exception {
        UserInfo info = userService.getUserInfo(uuid);
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)));
    }
    boolean validateUsername(String userName){
        return true;
    }
    boolean validatePassword(String password){
        return true;
    }
    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserRegistRequest  regist) throws Exception {
        UserInfo info = userService.findByUserName(regist.getUsername());
        if (info!= null){
            throw  new Exception("User already exist!");
        }

        if (!validateUsername(regist.getUsername())){
            throw new Exception("Username validate error");
        }

        if (!validatePassword(regist.getPassword())){
            throw new Exception("Password validate error");
        }


        Users login = new Users();
        login.setAccount( regist.getUsername() );

        String hashString =  bHasher.hashToString(12,regist.getPassword().toCharArray());
        String token = otpService.genOTPCode(hashString);
        login.setPassword( hashString );
        login.setCreate_at(LocalDateTime.now());
        login.setEnable(false);
        login.setToken(token);
        login.setTokenTime(LocalDateTime.now());
        login = userService.addUser(login);

        UserInfo userInfo = new UserInfo();
        userInfo.setName(regist.getName());
        userInfo.setAddress(regist.getAddress());
        userInfo.setDob(regist.getDob());
        userInfo.setPhone(regist.getPhonenumber());
        userInfo.setUUID(login.getUUID());
        userService.addUserInfo(userInfo);

        UserKey userKey = new UserKey();
        userKey.setPublicKey("");
        userKey.setPrivateKey("");
        userKey.setUUID(login.getUUID());
        userKeyService.save(userKey);
        otpService.sendOtp(userInfo.getPhone(),token);
        try {
            BufferedImage img = imageService.avatarLetterImage(userInfo.getName());
            String avatarPath = AppConfigure.getConfig("user.avatar.path");
            File dir = new File(avatarPath);
            File serverFile = new File(dir.getAbsolutePath() + File.separator + "avatar." + login.getUUID() + ".jpg");
            ImageIO.write(img,"jpg",serverFile);
        }catch (Exception e){

        }

        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,userInfo)));
    }
    @RequestMapping(value = "/users/verifyRegister", method = RequestMethod.POST)
    public ResponseEntity<?> verifyRegister(@RequestBody  VerifyRegister verify) throws Exception {

        Users user = userService.getUser(verify.getUuid());
        if (user == null){
            throw new Exception("User not found");
        }

        BCrypt.Result  resul = BCrypt.verifyer().verify(verify.getPassword().toCharArray(), user.getPassword());
        if (!user.getAccount().equals(verify.getUsername()) || resul == null || !resul.verified ) {
            throw new Exception("User not match");
        }

        String opt = user.getToken();
        if ( opt != null && !opt.trim().isEmpty())
        {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, user.getTokenTime());

            if (!opt.equals(verify.getOpt()))
            {
                throw new Exception("OTP not match");
            }
            if (duration.getSeconds() > 30){
                String token = otpService.genOTPCode(user.getPassword());
                user.setToken(token);
                user.setTokenTime( LocalDateTime.now() );
                userService.save(user);
                otpService.sendOtp(user.getUserInfo().getPhone(),token);
                throw new Exception("OPT time expired");
            }

        }else{
            return ResponseEntity.ok(new ApiResponse<JwtResponse>(2, AppConstant.ERROR_MESSAGE, null));
        }
        Device device = new Device();
        device.setUUID(UUID.randomUUID().toString());
        device.setUserUuid(user.getUUID());
        device.setDeviceOs("android");
        deviceService.addDevice(device);

        user.setEnable(true);
        user.setToken(null);
        userService.save(user);

        return ResponseEntity.ok(new ApiResponse<String>(0,AppConstant.SUCCESS_MESSAGE, ""));
    }


    @RequestMapping(value = "/users/info", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile() throws Exception {
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo info = userService.getUserInfo(detail.getUsername());
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)));
    }

    @RequestMapping(value = "/users/find/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable("name") String name) throws Exception {
        List<UserInfo> info = userService.findByName(name);
        return ResponseEntity.ok(new ApiResponse<List<UserInfoDTO>>(0,AppConstant.SUCCESS_MESSAGE, info.stream().map(user->UserInfoDTO.fromEntity(modelMapper,user)).collect(Collectors.toList())));
    }

	@RequestMapping(value = "/users/exist/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUserName(@PathVariable("username") String username) throws Exception {
        UserInfo info = userService.findByUserName(username);
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)  ));
    }

    @RequestMapping(value = "/users/prelogin/{device}/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUserName(@PathVariable("device") String deviceCode,@PathVariable("username") String username) throws Exception {
        UserInfo info = userService.findByUserName(username);
        System.out.println("find device: "+ deviceCode + " user uuid "+info.getUUID());
        Device device = deviceService.getDeviceByDeviceCode(info.getUUID(),deviceCode);
        if (device == null){
            Users user = userService.getUser(info.getUUID());
            String opt =  otpService.genOTPCode(user.getPassword());
            user.setToken(opt);
            user.setTokenTime(LocalDateTime.now());
            userService.save(user);
            otpService.sendOtp(info.getPhone(),opt);
            return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(1,AppConstant.ERROR_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)  ));
        }
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)  ));
    }

    @RequestMapping(value = "/users/quick/name", method = RequestMethod.POST)
    public ResponseEntity<?> changeUserInfoName(@RequestBody String username) throws Exception {
        username = username.trim().replace("\"","");
        if (username.isEmpty()){
            throw new Exception("NULL");
        }

        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo info = userService.getUserInfo(detail.getUsername());
        info.setName(username);
        userService.addUserInfo(info);

        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)  ));
    }


    @RequestMapping(value = "/users/suggest", method = RequestMethod.GET)
    public ResponseEntity<?> getSuggest() throws Exception {
        UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserInfo> users = userService.getRandomSuggest(detail.getUsername());
        List<UserInfoDTO> dtos = users.stream().map(m->UserInfoDTO.fromEntity(modelMapper,m)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(0,AppConstant.SUCCESS_MESSAGE, dtos));
    }





}
