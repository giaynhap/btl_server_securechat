package com.giaynhap.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.giaynhap.config.AppConstant;

public class SocketMessageCommandDTO {
    @JsonProperty("command")
    private AppConstant.MessageCommand command;
    private MessageDTO data;

    public AppConstant.MessageCommand getCommand() {
        return command;
    }

    public void setCommand(AppConstant.MessageCommand command) {
        this.command = command;
    }

    public MessageDTO getData() {
        return data;
    }

    public void setData(MessageDTO data) {
        this.data = data;
    }
}
