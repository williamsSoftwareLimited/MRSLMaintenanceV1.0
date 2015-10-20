package org.codebehind.mrslmaintenance.Entities;

import java.util.UUID;

/**
 * Created by Gavin on 23/02/2015.
 */
public class Image {
    private int id;
    private byte[] img;
    private String title;

    public Image(byte[] image, String title){
        setId(-1);
        setImage(image);
        setTitle(title);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public byte[] getImage() {
        return img;
    }
    public void setImage(byte[] img) {
        this.img = img;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
