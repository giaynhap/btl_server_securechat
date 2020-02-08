package com.giaynhap.controller;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserOnlineController  {
    private List<String> users;
    private static UserOnlineController instance;
    public static UserOnlineController getInstance(){
        return instance;
    }
    public UserOnlineController(){
        users = new ArrayList<>();
        instance =  this;
    }

    public void addUser(String uuid){
        System.out.println("user "+uuid+" online ");
        users.add(uuid);
    }
    public void removeUser(String uuid){
        System.out.println("user "+uuid+" disconnect ");
        users.remove(uuid);
    }
    public boolean isOnline(String uuid){
         for (String u : users){
             if (u.equals(uuid))
                 return true;
         }
         return false;
    }

}
