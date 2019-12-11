package com.giaynhap.model;

import java.io.Serializable;

public class ContactSerializable implements Serializable {
    private  String uuid;
    private String name;
    private Integer level = 0;

    public ContactSerializable(String uuid, String name, int level) {
        this.uuid = uuid;
        this.name = name;
        this.level = level;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
