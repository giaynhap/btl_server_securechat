package com.giaynhap.sticker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StikerInfo {
    public String model;
    public String name;
    public int col;
    public int row;
    public int width;
    public int height;
    public int type;
    public int num;
    public String file;
    public BufferedImage loadBitmap() throws IOException {
        BufferedImage bitmap = ImageIO.read(new File(file));
        return bitmap;
    }
    public BufferedImage loadBitmapById(int id) throws IOException {
        int x = id % col;
        int y = id /col;
        if (y > row|| y <0){
            return null;
        }
        BufferedImage source  = loadBitmap();
        BufferedImage destination = source.getSubimage(x*width,y*height,width,height);
        return destination;
    }
}
