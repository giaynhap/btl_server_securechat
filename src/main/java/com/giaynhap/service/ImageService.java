package com.giaynhap.service;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
@Component
public class ImageService {
   public BufferedImage resize(BufferedImage input,int width,int height){

       BufferedImage outputImage = new BufferedImage(width,
               height, input.getType());
       Graphics2D g2d = outputImage.createGraphics();
       g2d.drawImage(input, 0, 0, width, height, null);
       g2d.dispose();
        return outputImage;
   }
}
