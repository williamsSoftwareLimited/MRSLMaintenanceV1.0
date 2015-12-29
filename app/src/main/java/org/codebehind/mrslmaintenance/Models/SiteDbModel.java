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
            Log.wtf(LOG_TAG, "add: The site argument is null.");
            return -1;
        }

        contentValues=new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], 0);
        contentValues.put(FIELDS[NAME], site.getName());
        contentValues.put(FIELDS[ADDRESS], site.getAddress());
        contentValues.put(FIELDS[IMAGE_ID], site.getImageId());

        siteId=(int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);

        if (siteId>0) _list.add(site);
        else Log.d(LOG_TAG, "add: The site wasn't succesfully added to the database table.");

        return siteId;
    }

    public int update(Site site) {

     return updateWithDelete(site, false);
    }

    // Returns the siteId if successful and -1 if not
    // !!!!!!!!I've left this with an issue in that the _list is NOT changed if this delete is used!!!!!!!!!!!!
    public int delete(Site site){
        int rowCount;

        rowCount=updateWithDelete(site, true);

        if (rowCount<1) {
            Log.d(LOG_TAG, "delete: Delete unsuccessful check update message above.");
            return -1;
        }
        else return site.getId();
    }

    public ArrayList<Site> getList(){
        Cursor c;
        String query = "select "
                +"s."+FIELDS[ID]+", "
                +"s."+FIELDS[TIMESTAMP]+", "
                +"s."+FIELDS[DELETED]+", "
                +"s."+FIELDS[NAME]+", "
                +"s."+FIELDS[ADDRESS]+", "
                +"s."+FIELDS[IMAGE_ID]
                +" from " + TABLE + " s"
                +" where s."+FIELDS[DELETED]+"=0";

        // lazy load
        if (_list==null){
            _list=new ArrayList<>();

            c=DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
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

    private int updateWithDelete(Site site, boolean deleted){
        int rowCount; // invariance: 0 or 1
        ContentValues contentValues;
        Site siteInList;
        String whereClause;

        if (site==null) {

            Log.wtf(LOG_TAG, "update: The site argument is null.");
            return 0;
        }

        if (site.getId()==-1) {

            Log.wtf(LOG_TAG, "update: The site has an id of -1 and is therefore new.");
            return 0;
        }

        siteInList=getSite(site.getId());

        if (siteInList==null) {

            Log.e(LOG_TAG, "update: There's been a siteId (not equal to -1) but no Site in the list.");
            return 0; // Something seriously bad has happened here
        }

        contentValues=new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], deleted?1:0);
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

}