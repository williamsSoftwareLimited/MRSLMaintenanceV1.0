package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.drive.query.internal.FieldWithSortOrder;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.Abstract.IImageModel;
import org.codebehind.mrslmaintenance.StaticConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gavin on 07/01/2015.
 */
// this has an invariant context can't be null
public class ImageModel implements IImageModel {
    private Context context;
    public static final String TABLE="images", SELECTION="_id="
            , FILTER_SELECTION_START="title like '%",FILTER_SELECTION_END="%'";;
    private static final String[] FIELDS = new String[]{"_id", "image","title" };
    public static final int ID=0, IMAGE=1, TITLE=2;

    public ImageModel(Context context){
        if (context==null)throw new IllegalArgumentException("The context can't be null");
        this.context=context;
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
            return (int) DatabaseHelper.getInstance(context).getWritableDatabase().insert(TABLE, null, v);
        }else{
            return DatabaseHelper.getInstance(context).getWritableDatabase().update(TABLE,v,SELECTION+image.getId(),null);
        }
    }
    // invariant the image can't be null and the id>0
    public void delete(Image image){
        if (image==null || image.getId()<0)return;
        DatabaseHelper.getInstance(context).getWritableDatabase().delete(TABLE,SELECTION+image.getId(),null);
    }
    public Cursor getAll(){
        return DatabaseHelper.getInstance(context).getReadableDatabase().query(TABLE, FIELDS,null,null,null,null,null);
    }
    public Cursor getFiltered(String filter){
        return DatabaseHelper.getInstance(context).getReadableDatabase().query(TABLE, FIELDS,FILTER_SELECTION_START+filter+FILTER_SELECTION_END,null,null,null,null);
    }
    public Image getImage(int id){
        Cursor cursor;
        Image image;

        cursor = DatabaseHelper.getInstance(context).getReadableDatabase().query(TABLE, FIELDS,SELECTION+id,null,null,null,null);
        cursor.moveToFirst();
        image = new Image(cursor.getBlob(IMAGE),cursor.getString(TITLE));
        image.setId(cursor.getInt(ID));
        return image;
    }
    public Image assembleImage(Cursor c){
        Image i;

        if (c==null)return null;
        i=new Image(c.getBlob(IMAGE), c.getString(TITLE));
        i.setId(c.getInt(ID));
        return i;
    }
}