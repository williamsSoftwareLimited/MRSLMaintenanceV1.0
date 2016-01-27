package org.codebehind.mrslmaintenance.Entities;

import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;


/**
 * Created by Gavin on 23/02/2015.
 */
public class Image  extends AEntity {
    private byte[] img;
    private String title;

    public Image(byte[] image, String title){

        this(-1, image, title);
    }

    public Image(int id, byte[] image, String title){

        setId(id);
        setImage(image);
        setTitle(title);
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
