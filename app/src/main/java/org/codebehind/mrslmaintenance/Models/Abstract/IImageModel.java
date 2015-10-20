package org.codebehind.mrslmaintenance.Models.Abstract;

import android.database.Cursor;
import android.view.WindowId;

import org.codebehind.mrslmaintenance.Entities.Image;

/**
 * Created by Gavin on 26/02/2015.
 */
public interface IImageModel {
    public int insert(Image image);
    public void delete(Image image);
    public Cursor getAll();
    public Cursor getFiltered(String filter);
    public Image getImage(int id);
    public Image assembleImage(Cursor c);
}
