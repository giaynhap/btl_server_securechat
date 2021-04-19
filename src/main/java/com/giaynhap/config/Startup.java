package com.giaynhap.config;

import com.giaynhap.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private FilterService filterService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        filterService.startThread();
    }
}