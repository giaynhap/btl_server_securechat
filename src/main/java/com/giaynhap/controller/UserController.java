package com.giaynhap.controller;

import com.giaynhap.config.AppConstant;
import com.giaynhap.config.JwtTokenUtil;
import com.giaynhap.model.*;
import com.giaynhap.model.DTO.UserInfoDTO;
import com.giaynhap.service.DeviceServiceIml;
import com.giaynhap.service.JwtUserDetailsService;
import com.giaynhap.service.OptService;
import com.giaynhap.service.UserServiceIml;
import org.apache.coyote.http11.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    private OptService optService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Device device = authenticationRequest.getDevice();
        if ( device == null )
        {
            return ResponseEntity.ok(new ApiResponse<JwtResponse>(2, AppConstant.SUCCESS_MESSAGE, null));
        }
        final Users user = authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());


        if (deviceService.getDeviceByDeviceCode(user.getUUID(),device.getDeviceCode()) == null ){
                String opt = user.getToken();
                if ( opt != null && !opt.trim().isEmpty())
                {
                    if (!opt.equals(authenticationRequest.getToken()) )
                    {
                        return ResponseEntity.ok(new ApiResponse<JwtResponse>(2, AppConstant.SUCCESS_MESSAGE, null));

                    }

                    device.setUUID(UUID.randomUUID().toString());
                    device.setUserUuid(user.getUUID());
                    deviceService.addDevice(device);

                }else{

                    return ResponseEntity.ok(new ApiResponse<JwtResponse>(2, AppConstant.SUCCESS_MESSAGE, null));


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

        Users  user =   userService.login(username, password);
        if (user  == null){
            throw  new Exception("User not found");
        }
        return user;
    }



    @RequestMapping(value = "/users/info/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@PathVariable("uuid") String uuid) throws Exception {
        UserInfo info = userService.getUserInfo(uuid);
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)));
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
            String opt = optService.randomCode();
            Users user = userService.getUser(info.getUUID());
            user.setToken(opt);
            userService.save(user);
            optService.sendOpt(info.getPhone(),opt);
            return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(1,AppConstant.ERROR_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)  ));
        }
        return ResponseEntity.ok(new ApiResponse<UserInfoDTO>(0,AppConstant.SUCCESS_MESSAGE, UserInfoDTO.fromEntity(modelMapper,info)  ));
    }




}
