package org.codebehind.mrslmaintenance.Models;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.Models.Abstract.IImageModel;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gavin on 07/01/2015.
 */
// this has an invariant context can't be null
public class ImageModel  extends DbAbstractModelBase {

    private static final String LOG_TAG="ImageModel";
    public static final String TABLE="images", SELECTION="_id=";
    public static final String[] FIELDS = new String[]{ "_id", "image", "title" };
    public static final int ID=0, IMAGE=1, TITLE=2;

    public ImageModel(Context context){
        super(context, TABLE);
    }


    public int insert(Image image){
        ContentValues v;

        // if the image has an id then this is an update of just the title
        // if the image id is -1 then it's a new image
        if (image.getTitle()==null || image.getTitle()=="") image.setTitle(StaticConstants.IMAGE_TITLE);
        v= new ContentValues();
        v.put(FIELDS[TITLE], image.getTitle());

        if (image.getId()<0) {

            v.put(FIELDS[IMAGE], image.getImage());

            return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);

        }else{

            return DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE,v,SELECTION+image.getId(),null);

        }

    }

    // invariant the image can't be null and the id>0
    public void delete(Image image){

        if (image==null || image.getId()<0)return;

        DatabaseHelper.getInstance(_context).getWritableDatabase().delete(TABLE,SELECTION+image.getId(),null);
    }

    public Cursor getAll(){

        return DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,null,null,null,null,null);
    }

    public Image getImage(int id){
        Cursor cursor;
        Image image;

        cursor = DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,SELECTION+id,null,null,null,null);
        cursor.moveToFirst();

        image = new Image(cursor.getBlob(IMAGE),cursor.getString(TITLE));
        image.setId(cursor.getInt(ID));

        return image;
    }

    public byte[] readBytes(InputStream inputStream) {
        int len, bufferSize;
        ByteArrayOutputStream byteBuffer;
        byte[] buffer;

        byteBuffer = new ByteArrayOutputStream();
        bufferSize = 1024;
        buffer = new byte[bufferSize];

        try {

            while ((len = inputStream.read(buffer)) != -1) {

                byteBuffer.write(buffer, 0, len);
            }

        }catch(IOException e){

            Log.wtf(LOG_TAG, "readBytes: IOException thrown, trying to read the inputStream.");
            return null;
        }

        return byteBuffer.toByteArray();
    }

}