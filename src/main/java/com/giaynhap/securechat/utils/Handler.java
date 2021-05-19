package com.giaynhap.securechat.utils;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class Handler extends Object implements Runnable{
    boolean running = false;

    @Override
    public void run() {
        while ( running ){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!hasMessages()){
                continue;
            }

            Message m = messages.get(0);
            if ( m.expired < System.currentTimeMillis()) {
                handleMessage(m);
            }
        }
    }


    public  static class Message extends Object{
        public Runnable runnable;
        public long expired;
    }
    ArrayList<Message> messages;
    Thread t;
    public Handler(){
        messages = new ArrayList<>();
        running = true;
        t = new Thread(this);
        t.start();
    }

    public final Message postDelay(Runnable runnable,long delay){
        Message message = new Message();
        message.expired = System.currentTimeMillis() + delay;
        message.runnable = runnable;
        messages.add(message);
        return message;
    }

    public void handleMessage(Message msg){
        msg.runnable.run();
        messages.remove(msg);
    }

    public final boolean hasMessages(){
        return !messages.isEmpty();
    }
    public  void removeCallbacks(Runnable runnable){
        messages.removeIf(new Predicate<Message>() {
            @Override
            public boolean test(Message message) {
                return message.runnable == runnable;
            }
        });
    }
}