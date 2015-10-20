package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gavin on 23/08/2015.
 */
public class SiteDbModel {
    private Context _context;
    public static final String TABLE="site", FILTER_SELECTION_START="name like '%",FILTER_SELECTION_END="%'";
    private static final String[] FIELDS = new String[]{"_id", "ts","del", "name", "address", "imageId" };
    public static final int ID=0, TS=1, DEL=2, NAME=3, ADDRESS=4, IMAGE_ID=5;
    public SiteDbModel(Context context) {
        _context = context;
    }
    public int add(Site site) {
        if (site==null) return StaticConstants.BAD_DB;
        ContentValues v= new ContentValues();
        v.put(FIELDS[TS], new Date().getTime());
        v.put(FIELDS[DEL], false);
        v.put(FIELDS[NAME], site.getName());
        v.put(FIELDS[ADDRESS], site.getAddress());
        v.put(FIELDS[IMAGE_ID], site.getImageId());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }
    public Cursor getAll(){
        return DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,null,null,null,null,null);
    }
}