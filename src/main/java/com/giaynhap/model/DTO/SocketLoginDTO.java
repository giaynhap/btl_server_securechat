package com.giaynhap.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.giaynhap.config.AppConstant;

public class SocketLoginDTO {
    @JsonProperty("command")
    AppConstant.SocketLoginCommand command;
    @JsonProperty("data")
    String data;

    public AppConstant.SocketLoginCommand getCommand() {
        return command;
    }

    public void setCommand(AppConstant.SocketLoginCommand command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
