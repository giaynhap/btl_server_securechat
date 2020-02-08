package com.giaynhap.sticker;

import com.giaynhap.config.AppConfigure;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class StikerLoader {
    ArrayList<StikerInfo> stickers;
    private static StikerLoader instance;
    public static StikerLoader getInstance(){
        if (instance == null){
            instance = new StikerLoader();
        }
        return instance;
    }
    private StikerLoader(){
        stickers = new ArrayList<>();
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() throws IOException {
        Wini  ini = new Wini(new File(AppConfigure.getStikerConfigPath()));
        Collection<Profile.Section> list = ini.values();
        for(Profile.Section section : list){
            StikerInfo info = new StikerInfo();
            info.model = section.getName();
            info.name =   ini.fetch(section.getName(),"name");
            info.file =   ini.fetch(section.getName(),"file");
            info.col =   Integer.decode(ini.fetch(section.getName(),"col"));
            info.row =   Integer.decode(ini.fetch(section.getName(),"row"));
            info.width =   Integer.decode(ini.fetch(section.getName(),"width"));
            info.height =   Integer.decode(ini.fetch(section.getName(),"height"));
            info.num =   Integer.decode(ini.fetch(section.getName(),"num"));
            stickers.add(info);
        }
    }

    public ArrayList<StikerInfo> getStickers() {
        return stickers;
    }

    public void setStickers(ArrayList<StikerInfo> stickers) {
        this.stickers = stickers;
    }
    public StikerInfo getStiker(String model){
       return this.stickers.stream().filter(stikerInfo -> stikerInfo.model.equals(model)).findFirst().get();
    }
}
