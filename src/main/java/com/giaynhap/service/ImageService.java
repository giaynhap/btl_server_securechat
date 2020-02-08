package com.giaynhap.service;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
@Component
public class ImageService {
    private static String colors[] = new String []{
            "#706fd3", "#ff5252","#34ace0","#33d9b2",  "#ff793f","#ffb142","#c56cf0","#FC427B","#2C3A47","#7d5fff"};

    public BufferedImage cropAvatar(BufferedImage input)
    {

        int w = input.getWidth();
        int h = input.getHeight();
        int size = (w>h)?w:h;
        int x = (w-size)/2;
        int y = (h-size)/2;
        return input.getSubimage(x,y,w,h);
    }

   public BufferedImage resize(BufferedImage input,int width,int height){

       BufferedImage outputImage = new BufferedImage(width,
               height, BufferedImage.TYPE_INT_RGB);
       Graphics2D g2d = outputImage.createGraphics();
       g2d.drawImage(input, 0, 0, width, height, null);
       g2d.dispose();
        return outputImage;
   }

   private String getNameLetter(String name){
        String[] split = name.split(" ");
        return  split[split.length-1].substring(0,1).toUpperCase();
   }
   private int getColorIndex(String name){
       return ((int)name.toCharArray()[0])%colors.length;

   }
    public BufferedImage avatarLetterImage(String name){
       BufferedImage outputImage = new BufferedImage(256,
               256, BufferedImage.TYPE_INT_RGB);

       String text = getNameLetter(name);

       Graphics2D g2d = outputImage.createGraphics();
       Font myFont = new Font ("Arial", 1, 150);
       g2d.setBackground(Color.decode(colors[getColorIndex(text)]));
       g2d.clearRect(0,0,256,256);
       g2d.setFont(myFont);
       g2d.setColor(Color.WHITE);

       FontMetrics metrics = g2d.getFontMetrics(myFont);
       int x = 256/2 - metrics.stringWidth(text)/2;
       int y = 256/2 + metrics.getAscent()/2-10;
       g2d.drawString(text,x,y);

       g2d.dispose();
       return outputImage;
   }
}
