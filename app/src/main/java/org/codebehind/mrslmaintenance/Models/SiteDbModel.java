package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.android.gms.games.GamesMetadata;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gavin on 23/08/2015.
 */
public class SiteDbModel extends DbAbstractModelBase {

    public static final String TABLE="Site";
    public static final String[] FIELDS = new String[]{"_id", "timestamp", "deleted", "name", "address", "imageId" };
    public static final int ID=0, TIMESTAMP=1, DELETED=2, NAME=3, ADDRESS=4, IMAGE_ID=5;
    private static final String LOG_TAG="SiteDbModel";
    private ArrayList<Site> _list;

    public SiteDbModel(Context context) {
        super(context, TABLE);

        getList(); // set the list
    }

    public int add(Site site) {
        ContentValues contentValues;
        int siteId;

        if (site==null) {
            Log.d(LOG_TAG, "add: The site argument is null.");
            return -1;
        }

        contentValues=new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], false);
        contentValues.put(FIELDS[NAME], site.getName());
        contentValues.put(FIELDS[ADDRESS], site.getAddress());
        contentValues.put(FIELDS[IMAGE_ID], site.getImageId());

        siteId=(int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);

        if (siteId>0) _list.add(site);
        else Log.d(LOG_TAG, "add: The site wasn't succesfully added to the database table.");

        return siteId;
    }

    public int update(Site site){
        int rowCount; // invariance: 0 or 1
        ContentValues contentValues;
        Site siteInList;
        String whereClause;

        if (site==null) {

            Log.d(LOG_TAG, "update: The site argument is null.");
            return 0;
        }

        if (site.getId()==-1) {

            Log.d(LOG_TAG, "update: The site has an id of -1 and is therefore new.");
            return 0;
        }

        siteInList=getSite(site.getId());

        if (siteInList==null) {

            Log.e(LOG_TAG, "update: There's been a siteId (not equal to -1) but no Site in the list.");
            return 0; // Something seriously bad has happened here
        }

        contentValues=new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[NAME], site.getName());
        contentValues.put(FIELDS[ADDRESS], site.getAddress());
        //contentValues.put(FIELDS[IMAGE_ID], site.getImageId());

        whereClause=FIELDS[ID]+"="+site.getId();

        rowCount=DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE, contentValues, whereClause, null);

        if (rowCount>0) {
            _list.remove(siteInList);
            _list.add(site);
        }

        return rowCount;
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
                Site e=new Site(c.getInt(ID), c.getString(NAME), c.getString(ADDRESS));
                e.setImageId(c.getInt(IMAGE_ID));
                _list.add(e);
                c.moveToNext();
            }
        }
        return _list;
    }

    public Site getSite(int siteId){

        for (Site site: _list){

            if (site.getId()==siteId) return site;
        }

        return new Site(-1, "", "");
    }

}