package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gavin on 23/08/2015.
 */
public class SiteDbModel extends DbAbstractModel<Site> {

    public static final String TABLE="site", FILTER_SELECTION_START="name like '%",FILTER_SELECTION_END="%'";
    private static final String[] FIELDS = new String[]{"_id", "ts","del", "name", "address", "imageId" };
    public static final int ID=0, TS=1, DEL=2, NAME=3, ADDRESS=4, IMAGE_ID=5;

    public SiteDbModel(Context context) {
        super(context);
        getlist(); // set the list
    }

    @Override
    public Site getEntity(int id) {
        return null;
    }

    public int add(Site site) {
        if (site==null) return StaticConstants.BAD_DB;
        _list.add(site);

        ContentValues v= new ContentValues();

        v.put(FIELDS[TS], new Date().getTime());
        v.put(FIELDS[DEL], false);
        v.put(FIELDS[NAME], site.getName());
        v.put(FIELDS[ADDRESS], site.getAddress());
        v.put(FIELDS[IMAGE_ID], site.getImageId());

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }

    @Override
    public ArrayList<Site> getList() {
        return null;
    }

    @Override
    public ArrayList<Site> getList(ArrayList<String> params) {
        return null;
    }

    @Override
    public ArrayList<Site> getFilterList(String filter) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Site entity) {

    }

    public Cursor getcursor(){
        return DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,null,null,null,null,null);
    }

    public ArrayList<Site> getlist(){
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