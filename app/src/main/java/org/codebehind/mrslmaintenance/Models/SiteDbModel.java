package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gavin on 23/08/2015.
 */
public class SiteDbModel extends DbAbstractModelBase {

    public static final String TABLE="Site", FILTER_SELECTION_START="name like '%",FILTER_SELECTION_END="%'";
    public static final String[] FIELDS = new String[]{"_id", "ts","del", "name", "address", "imageId" };
    public static final int ID=0, TS=1, DEL=2, NAME=3, ADDRESS=4, IMAGE_ID=5;
    private ArrayList<Site> _list;

    public SiteDbModel(Context context) {
        super(context, TABLE);
        getList(); // set the list
    }

    public Site getEntity(int id) {
        return null;
    }

    public int add(Site site) {
        ContentValues v;
        int dbRetNo;
        if (site==null) return StaticConstants.BAD_DB;

        v= new ContentValues();

        v.put(FIELDS[TS], new Date().getTime());
        v.put(FIELDS[DEL], false);
        v.put(FIELDS[NAME], site.getName());
        v.put(FIELDS[ADDRESS], site.getAddress());
        v.put(FIELDS[IMAGE_ID], site.getImageId());

        dbRetNo=(int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
        if (dbRetNo>0) _list.add(site);
        DatabaseHelper.getInstance(_context).getWritableDatabase().close();

        return dbRetNo;
    }

    public Cursor getcursor(){
        return DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,null,null,null,null,null);
    }

    public ArrayList<Site> getList(){
        Cursor c;
        // lazy load
        if (_list==null){
            _list=new ArrayList<>();
            c= getcursor();
            c.moveToFirst();

            while(c.isAfterLast()==false){
                Site e=new Site();
                e.setId(c.getInt(ID));
                e.setName(c.getString(NAME));
                e.setAddress(c.getString(ADDRESS));
                e.setImageId(c.getInt(IMAGE_ID));
                _list.add(e);
                c.moveToNext();
            }
        }
        return _list;
    }

}